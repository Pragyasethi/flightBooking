package com.flightapp.flightservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.flightservice.dto.FlightRequest;
import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Flight;

public interface FlightService {

	FlightResponse addNewFlightDetails(FlightRequest flightRequest) throws ResourceNotFoundException;

	List<FlightResponse> findAll();

	Flight getAllByAirlineId(Long airlineId);

	FlightResponse updateFlightDetails(FlightRequest flightRequest);

	ResponseEntity<?> deleteFlightByIdAndAirlineId(Long flightId, Long airlineId);

	List<FlightResponse> getQueryResult(List<SearchCriteria> searchFilter,String date);

	ResponseEntity<String> deleteFlight(Long id);

	FlightResponse findById(Long id);
	
	/**
	 * To update status when airline or airport's status is changed.
	 * @param Status
	 * @param airportId
	 * @param airlineId
	 */
	void updateStatus(Integer status, String airportId , String airlineId);

}
