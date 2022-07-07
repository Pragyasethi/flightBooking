package com.flightapp.flightservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flightapp.flightservice.dto.AirportResponse;
import com.flightapp.flightservice.model.Airport;
import com.flightapp.flightservice.repository.AirportRepository;
import com.flightapp.flightservice.service.AirportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

	private final AirportRepository airportRepository;

	private AirportResponse mapToDto(Airport airport) {
		return AirportResponse.builder()
				.airportId(airport.getId().toString())
				.airportCode(airport.getAirportCode())
				.airportLocation(airport.getAirportLocation())
				.airportName(airport.getAirportName())
				.build();
	}


	@Override
	public List<AirportResponse> findAll() {
		List<Airport> airports = airportRepository.findAll();
		return airports.stream().map(this::mapToDto).collect(Collectors.toList());
	}

}
