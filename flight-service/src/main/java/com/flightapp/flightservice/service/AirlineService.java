package com.flightapp.flightservice.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;

public interface AirlineService {

	AirlineResponse registerNewAirline(AirlineRequest airlineRequest);

	List<AirlineResponse> findAll();

	AirlineResponse updateAirlineById(Long airlineId, AirlineRequest airlineRequest);

	ResponseEntity<String> deleteAirlineById(Long airlineId);
	
	List<AirlineResponse> getQueryResult(List<SearchCriteria> searchFilter);

	AirlineResponse findById(Long id);

	AirlineResponse updateAirlineDetails(@Valid AirlineRequest airlineRequest);


}
