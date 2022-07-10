package com.flightapp.flightservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.commonmodule.specifications.SpecificationBuilder;
import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Airline;
import com.flightapp.flightservice.repository.AirlineRepository;
import com.flightapp.flightservice.service.AirlineService;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

	private final AirlineRepository airlineRepository;
	
	private final FlightService flightService;


	private final SpecificationBuilder<Airline> specificationBuilder;

	private AirlineResponse mapToDto(Airline airline) {
		return AirlineResponse.builder().airlineId(airline.getId().toString()).airlineName(airline.getAirlineName())
				.airlineLogo(airline.getAirlineLogo())
				.status(StatusEnum.fromStatus(airline.getStatus()).getStatusName()).build();
	}

	private Airline mapToModel(AirlineRequest airlineRequest) {
		return Airline.builder().airlineName(airlineRequest.getAirlineName())
				.airlineLogo(airlineRequest.getAirlineLogo())
				.status(airlineRequest.getStatus() == null ? StatusEnum.ACTIVE.getStatus() : airlineRequest.getStatus())
				.build();
	}

	private Airline mapToModel(AirlineRequest airlineRequest, Airline airline) {
		airline.setAirlineName(airlineRequest.getAirlineName());
		airline.setAirlineLogo(airlineRequest.getAirlineLogo());
		airline.setStatus(airlineRequest.getStatus());
		return airline;
	}

	@Override
	public AirlineResponse registerNewAirline(AirlineRequest airlineRequest) {
		return mapToDto(airlineRepository.save(mapToModel(airlineRequest)));

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
	public ResponseEntity<String> deleteAirlineById(Long airlineId) {
		return airlineRepository.findById(airlineId).map(airline -> {
			airlineRepository.delete(airline);
			return ResponseEntity.ok("Airline Details deleted for Airline Name : " + airline.getAirlineName());
		}).orElseThrow(() -> new ResourceNotFoundException("Airline Details not found for id: " + airlineId));
	}

	@Override
	public List<AirlineResponse> getQueryResult(List<SearchCriteria> searchFilter) {
		List<Airline> airlineList;
		if (searchFilter.isEmpty()) {
			airlineList = airlineRepository.findAll();
		} else {
			airlineList = airlineRepository.findAll(specificationBuilder.getSpecificationFromFilters(searchFilter));
		}
		return airlineList.stream().map(this::mapToDto).collect(Collectors.toList());

	}

	@Override
	public AirlineResponse findById(Long id) {
		Airline airline = airlineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Airline Id " + id + " Not found "));
		return mapToDto(airline);
	}

	@Override
	public AirlineResponse updateAirlineDetails(@Valid AirlineRequest airlineRequest) {
		Airline fetchedAirline = airlineRepository.findById(airlineRequest.getIdAsLong()).orElseThrow(() -> new ResourceNotFoundException(
				"Airline Id " + airlineRequest.getIdAsLong() + " Not found "));
		airlineRepository.save(mapToModel(airlineRequest,fetchedAirline));	
		
		if (fetchedAirline.getStatus().compareTo(airlineRequest.getStatus()) != 0) {
			flightService.updateStatus(airlineRequest.getStatus(), null, fetchedAirline.getId().toString());
		}

		return mapToDto(fetchedAirline);
	}
}
