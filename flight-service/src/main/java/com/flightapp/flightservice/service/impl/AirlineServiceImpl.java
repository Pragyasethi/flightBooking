package com.flightapp.flightservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Airline;
import com.flightapp.flightservice.repository.AirlineRepository;
import com.flightapp.flightservice.service.AirlineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

	private final AirlineRepository airlineRepository;

	private AirlineResponse mapToDto(Airline airline) {
		return AirlineResponse.builder().airlineId(airline.getId().toString()).airlineName(airline.getAirlineName())
				.airlineLogo(airline.getAirlineLogo()).status(StatusEnum.fromStatus(airline.getStatus()).getStatusName()).build();
	}

	private Airline mapToModel(AirlineRequest airlineRequest) {
		return Airline.builder().airlineName(airlineRequest.getAirlineName())
				.airlineLogo(airlineRequest.getAirlineLogo()).build();
	}

	@Override
	public AirlineResponse registerNewAirline(AirlineRequest airlineRequest) {
		try {
		Airline airline = Airline.builder().airlineName(airlineRequest.getAirlineName())
				.airlineLogo(airlineRequest.getAirlineLogo()).build();
		return mapToDto(airlineRepository.save(airline));
		}catch(ConstraintViolationException e) {
			throw new ConstraintViolationException(e.getMessage(), null);
			
		}

	}

	@Override
	public List<AirlineResponse> findAll() {
		List<Airline> airlines = airlineRepository.findAll();
		return airlines.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public AirlineResponse updateAirlineById(Long airlineId, AirlineRequest airlineRequest) {
		Airline updateAirline = airlineRepository.findById(airlineId)
				.map(airline -> airlineRepository.save(mapToModel(airlineRequest)))
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airline Details not found for name: " + airlineRequest.getAirlineName()));
		return mapToDto(updateAirline);
	}

	@Override
	public ResponseEntity<?> deleteAirlineById(Long airlineId) {
		return airlineRepository.findById(airlineId).map(airline -> {
			airlineRepository.delete(airline);
			return ResponseEntity.ok("Airline Details deleted for Airline Name : " + airline.getAirlineName());
		}).orElseThrow(() -> new ResourceNotFoundException("Airline Details not found for id: " + airlineId));
	}

}
