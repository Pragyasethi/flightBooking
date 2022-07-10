package com.flightapp.flightservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.commonmodule.utility.SearchUtility;
import com.flightapp.flightservice.dto.FlightRequest;
import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class FlightController {

	private final FlightService flightService;

	/**
	 * To get all flights. user can filter the search using this string in url.
	 * ?search=source:3,destination:2,scheduledfor~monday
	 * 
	 * @return
	 */
	@GetMapping("/flight")
	public List<FlightResponse> getAllFlights(@RequestParam(value = "search") String search, @RequestParam(value="departureDate") String date) {
		return flightService.getQueryResult(SearchUtility.searchFilter(search),date);
	}

	/**
	 * To add flight Details in Database.
	 * 
	 * @param flightRequest
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/flight")
	public FlightResponse addFlightDetails(@Valid @RequestBody FlightRequest flightRequest) {
		return flightService.addNewFlightDetails(flightRequest);
	}

	/**
	 * To update the existing flight details.
	 * 
	 * @param flightRequest
	 * @return
	 */
	@PutMapping("/flight")
	public FlightResponse updateFlightDetails(@RequestBody FlightRequest flightRequest) {
		return flightService.updateFlightDetails(flightRequest);
	}

	/**
	 * To Delete Flight Details.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/flight/{id}")
	public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
		return flightService.deleteFlight(id);

	}

	/**
	 * To get Flight by Id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/flight/{id}")
	public FlightResponse getFlightById(@PathVariable Long id) {
		return flightService.findById(id);

	}

}
