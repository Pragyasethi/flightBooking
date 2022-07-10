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
import com.flightapp.flightservice.dto.AirportRequest;
import com.flightapp.flightservice.dto.AirportResponse;
import com.flightapp.flightservice.service.AirportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class AirportController {

	private final AirportService airportService;

	/**
	 * To get Airport List.
	 * ?search=status:1
	 * 
	 * @param search
	 * @return
	 */
	@GetMapping("/airport")
	public List<AirportResponse> getAllAirports(@RequestParam(value = "search") String search) {
		return airportService.getQueryResult(SearchUtility.searchFilter(search));

	}

	/**
	 * To add new Airport.
	 * 
	 * @param airportRequest
	 * @return
	 */
	@PostMapping("/airport")
	public AirportResponse addAirport(@Valid @RequestBody AirportRequest airportRequest) {
		return airportService.addAirport(airportRequest);

	}

	/**
	 * To get Airport by Id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/airport/{id}")
	public AirportResponse getAirportById(@PathVariable Long id) {
		return airportService.findById(id);

	}

	/**
	 * To update Airport Details.
	 * 
	 * @param airportRequest
	 * @return
	 */
	@PutMapping("/airport")
	public AirportResponse updateAirport(@Valid @RequestBody AirportRequest airportRequest) {
		return airportService.updateAirportDetails(airportRequest);

	}

	/**
	 * To Delete Airport Details.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/airport/{id}")
	public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
		return airportService.deleteAirportId(id);

	}


}
