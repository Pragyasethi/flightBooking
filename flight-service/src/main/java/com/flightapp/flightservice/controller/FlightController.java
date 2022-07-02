package com.flightapp.flightservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.commonmodule.utility.JsonUtil;
import com.flightapp.commonmodule.utility.SearchUtility;
import com.flightapp.flightservice.dto.AirlineRequest;
import com.flightapp.flightservice.dto.AirlineResponse;
import com.flightapp.flightservice.dto.FlightRequest;
import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.service.AirlineService;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

	private final FlightService flightService;
	
	private final AirlineService airlineService;

	private final KafkaTemplate<String, String> kafkaTemplate;

	
	/**
	 * To register new Airline.
	 * @param airlineRequest
	 * @return
	 */
	@PostMapping("/airline")
	public AirlineResponse registerAirline(@Valid @RequestBody AirlineRequest airlineRequest) {
		return airlineService.registerNewAirline(airlineRequest);

	}

	@GetMapping("/airline")
	public List<AirlineResponse> getAllAirlines() {
		return airlineService.findAll();

	}

	@PutMapping("/airline")
	public AirlineResponse updateAirline(@PathVariable Long airlineId, @RequestBody AirlineRequest airlineRequest) {
		return airlineService.updateAirlineById(airlineId, airlineRequest);

	}
	
	@DeleteMapping("/{airlineId}")
	public ResponseEntity<?> deleteAirline(@PathVariable Long airlineId) {
		return airlineService.deleteAirlineById(airlineId);

	}

	/**
	 * To add flight Details in Database.
	 * @param flightRequest
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PostMapping
	public FlightResponse addFlightDetails(@Valid @RequestBody FlightRequest flightRequest) throws ResourceNotFoundException{
		FlightResponse response= flightService.addNewFlightDetails(flightRequest);
		//To update the capacity in Inventory
		kafkaTemplate.send("inventory-service", "abs", JsonUtil.toJson(response));
		return response;
	}

	/**
	 * To get all flights.
	 * user can filter the search using this string in url.
	 *   ?search=id:7,price>20.
	 * @return
	 */
	@GetMapping
	public List<FlightResponse> getAllFlights(@RequestParam(value = "search") String search) {
		return flightService.getQueryResult(SearchUtility.searchFilter(search));
	}

	/**
	 * To update the existing flight details.
	 * @param flightRequest
	 * @return
	 */
	@PutMapping
	public FlightResponse updateFlightDetails(@RequestBody FlightRequest flightRequest) {
		return flightService.updateFlightDetails(flightRequest);
	}

//	@GetMapping("/airline/{airlineId}/flights")
//	public Flight getAllFlightsByAirlineId(@PathVariable(value = "airlineId") Long airlineId) {
//		return flightService.getAllByAirlineId(airlineId);
//	}

	/**
	 * To delete Flight From System.
	 * @param airlineId
	 * @param flightId
	 * @return
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteFlightById(@RequestParam(value = "airlineId") Long airlineId,
			@RequestParam(value = "flightId") Long flightId) {
		return flightService.deleteFlightByIdAndAirlineId(flightId, airlineId);
	}

}
