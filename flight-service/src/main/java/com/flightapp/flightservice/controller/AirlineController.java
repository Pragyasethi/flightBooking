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

import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;
import com.flightapp.flightservice.service.AirlineService;
import com.flightapp.flightservice.utility.SearchUtility;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class AirlineController {

	private final AirlineService airlineService;

	/**
	 * To get Airline List.
	 * ?search=status:1
	 * 
	 * @param search
	 * @return
	 */
	@GetMapping("/airline")
	public List<AirlineResponse> getAllAirlines(@RequestParam(value = "search") String search) {
		return airlineService.getQueryResult(SearchUtility.searchFilter(search));

	}

	/**
	 * To add new Airline.
	 * 
	 * @param airlineRequest
	 * @return
	 */
	@PostMapping("/airline")
	public AirlineResponse registerAirline(@Valid @RequestBody AirlineRequest airlineRequest) {
		return airlineService.registerNewAirline(airlineRequest);

	}

	/**
	 * To get Airline by Id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/airline/{id}")
	public AirlineResponse getAirlineById(@PathVariable Long id) {
		return airlineService.findById(id);

	}

	/**
	 * To update Airline Details.
	 * 
	 * @param airlineRequest
	 * @return
	 */
	@PutMapping("/airline")
	public AirlineResponse updateAirport(@Valid @RequestBody AirlineRequest airlineRequest) {
		return airlineService.updateAirlineDetails(airlineRequest);

	}

	/**
	 * To Delete Airline Details.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/airline/{id}")
	public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
		return airlineService.deleteAirlineById(id);

	}

}
