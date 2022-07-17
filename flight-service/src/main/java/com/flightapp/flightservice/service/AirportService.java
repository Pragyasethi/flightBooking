package com.flightapp.flightservice.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.flightapp.flightservice.dto.AirportRequest;
import com.flightapp.flightservice.dto.AirportResponse;
import com.flightapp.flightservice.model.Airport;
import com.flightapp.flightservice.utility.SearchCriteria;

public interface AirportService {

	List<AirportResponse> findAll();

	Airport findByAirportCode(String airportCode);

	AirportResponse addAirport(@Valid AirportRequest airportRequest);

	AirportResponse findById(Long id);

	AirportResponse updateAirportDetails(@Valid AirportRequest airportRequest);

	ResponseEntity<String> deleteAirportId(Long id);
	
	List<AirportResponse> getQueryResult(List<SearchCriteria> searchFilter);


}
