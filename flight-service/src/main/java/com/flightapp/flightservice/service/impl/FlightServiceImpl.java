package com.flightapp.flightservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.commonmodule.specifications.SpecificationBuilder;
import com.flightapp.flightservice.dto.FlightRequest;
import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Airline;
import com.flightapp.flightservice.model.Flight;
import com.flightapp.flightservice.repository.AirlineRepository;
import com.flightapp.flightservice.repository.FlightRepository;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

	private final FlightRepository flightRepository;

	private final AirlineRepository airlineRepository;
	
	private final SpecificationBuilder<Flight> specificationBuilder;


	private FlightResponse mapToDto(Flight flight) {
		return FlightResponse.builder().flightId(flight.getId().toString()).flightNumber(flight.getFlightNumber())
				.price(flight.getPrice()).status(StatusEnum.fromStatus(flight.getStatus()).getStatusName()).airlineId(flight.getAirline().getId().toString())
				.build();
	}

	private Flight mapToModel(FlightRequest flightRequest) {
		return Flight.builder().flightNumber(flightRequest.getFlightNumber()).price(flightRequest.getPrice()).build();
	}

	@Override
	@Transactional
	public FlightResponse addNewFlightDetails(FlightRequest flightRequest) throws ResourceNotFoundException {
		Flight flight = mapToModel(flightRequest);

		Airline airline = airlineRepository.findById(flightRequest.getAirlineIdAsLong())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airline with id " + flightRequest.getAirlineId() + " Not found to add Flight Details "));
		flight.setAirline(airline);
		FlightResponse response = mapToDto(flightRepository.save(flight));
		response.setCapacity(flightRequest.getCapacity());
		return response;

	}

	@Override
	public List<FlightResponse> findAll() {
		List<Flight> flights = flightRepository.findAll();
		return flights.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public Flight getAllByAirlineId(Long airlineId) {
		return flightRepository.findByAirlineId(airlineId);
	}

	@Override
	public FlightResponse updateFlightDetails(FlightRequest flightRequest) {
		if (!airlineRepository.existsById(flightRequest.getAirlineIdAsLong())) {
			throw new ResourceNotFoundException("Airline with id " + flightRequest.getAirlineId() + " not found");
		}
		Flight updatedFlight = flightRepository.findById(flightRequest.getIdAsLong())
				.map(flight -> flightRepository.save(mapToModel(flightRequest)))
				.orElseThrow(() -> new ResourceNotFoundException(
						"Flight Details not found for id: " + flightRequest.getId()));
		return mapToDto(updatedFlight);
	}

	@Override
	public ResponseEntity<?> deleteFlightByIdAndAirlineId(Long flightId, Long airlineId) {
		return flightRepository.findByIdAndAirlineId(flightId, airlineId).map(flight -> {
			flightRepository.delete(flight);
			return ResponseEntity.ok("Flight Details deleted for Flight Number : " + flight.getFlightNumber());
		}).orElseThrow(() -> new ResourceNotFoundException("Flight Details not found for id: " + flightId));
	}

	@Override
	public List<FlightResponse> getQueryResult(List<SearchCriteria> searchFilter) {
		List<Flight> flightsList;
		if (searchFilter.isEmpty()) {
			flightsList = flightRepository.findAll();
		} else {
			flightsList = flightRepository.findAll(specificationBuilder.getSpecificationFromFilters(searchFilter));
		}
		return flightsList.stream().map(this::mapToDto).collect(Collectors.toList());

	}

}
