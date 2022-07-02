package com.flightapp.flightservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;

public interface AirlineService {

	AirlineResponse registerNewAirline(AirlineRequest airlineRequest);

	List<AirlineResponse> findAll();

	AirlineResponse updateAirlineById(Long airlineId, AirlineRequest airlineRequest);

	ResponseEntity<?> deleteAirlineById(Long airlineId);

}
