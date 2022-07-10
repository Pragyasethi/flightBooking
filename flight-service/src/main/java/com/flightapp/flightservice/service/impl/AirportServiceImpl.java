package com.flightapp.flightservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.commonmodule.specifications.SpecificationBuilder;
import com.flightapp.flightservice.dto.AirportRequest;
import com.flightapp.flightservice.dto.AirportResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Airport;
import com.flightapp.flightservice.repository.AirportRepository;
import com.flightapp.flightservice.service.AirportService;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportServiceImpl implements AirportService {

	private final AirportRepository airportRepository;

	private final FlightService flightService;

	private final SpecificationBuilder<Airport> specificationBuilder;

	private AirportResponse mapToDto(Airport airport) {
		return AirportResponse.builder().airportId(airport.getId().toString()).airportCode(airport.getAirportCode())
				.airportLocation(airport.getAirportLocation()).airportName(airport.getAirportName())
				.status(airport.getStatus().toString()).build();
	}

	private Airport mapToModel(AirportRequest airportRequest) {
		return Airport.builder().airportCode(airportRequest.getAirportCode())
				.airportLocation(airportRequest.getAirportLocation()).airportName(airportRequest.getAirportName())
				.status(airportRequest.getStatus() == null ? StatusEnum.ACTIVE.getStatus() : airportRequest.getStatus())
				.build();
	}

	private Airport mapToModel(AirportRequest airportRequest, Airport airport) {
		airport.setAirportCode(airportRequest.getAirportCode());
		airport.setAirportLocation(airportRequest.getAirportLocation());
		airport.setAirportName(airportRequest.getAirportName());
		airport.setStatus(airportRequest.getStatus());
		return airport;
	}

	@Override
	public List<AirportResponse> findAll() {
		List<Airport> airports = airportRepository.findAll();
		return airports.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public Airport findByAirportCode(String airportCode) {
		return airportRepository.findByAirportCode(airportCode).orElseThrow(
				() -> new ResourceNotFoundException("Airport " + airportCode + " Not found to add Flight Details "));
	}

	@Override
	public AirportResponse addAirport(@Valid AirportRequest airportRequest) {
		return mapToDto(airportRepository.save(mapToModel(airportRequest)));

	}

	@Override
	public AirportResponse findById(Long id) {
		Airport airport = airportRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Airport Id " + id + " Not found "));
		return mapToDto(airport);
	}

	@Override
	public AirportResponse updateAirportDetails(@Valid AirportRequest airportRequest) {
		Airport fetchedAirport = airportRepository.findById(airportRequest.getIdAsLong()).orElseThrow(
				() -> new ResourceNotFoundException("Airport Id " + airportRequest.getIdAsLong() + " Not found "));
		airportRepository.save(mapToModel(airportRequest, fetchedAirport));
		if (fetchedAirport.getStatus().compareTo(airportRequest.getStatus()) != 0) {
			flightService.updateStatus(airportRequest.getStatus(), fetchedAirport.getId().toString(), null);
		}

		return mapToDto(fetchedAirport);
	}

	@Override
	public ResponseEntity<String> deleteAirportId(Long id) {
		return airportRepository.findById(id).map(airport -> {
			airportRepository.delete(airport);
			return ResponseEntity.ok("Airport Details deleted for Airport Name : " + airport.getAirportName());
		}).orElseThrow(() -> new ResourceNotFoundException("Airport Details not found for id: " + id));
	}

	@Override
	public List<AirportResponse> getQueryResult(List<SearchCriteria> searchFilter) {
		List<Airport> airportList;
		if (searchFilter.isEmpty()) {
			airportList = airportRepository.findAll();
		} else {
			airportList = airportRepository.findAll(specificationBuilder.getSpecificationFromFilters(searchFilter));
		}
		return airportList.stream().map(this::mapToDto).collect(Collectors.toList());

	}

}
