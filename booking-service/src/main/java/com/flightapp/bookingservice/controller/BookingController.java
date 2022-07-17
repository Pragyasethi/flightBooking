package com.flightapp.bookingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.bookingservice.dto.CommonResponse;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.bookingservice.utility.JsonUtil;
import com.flightapp.bookingservice.utility.SearchUtility;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
@CrossOrigin

public class BookingController {

	private final BookingService bookingService;

	private final KafkaTemplate<String, String> kafkaTemplate;

	private final WebClient.Builder webClientBuilder;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
		BookingResponseDto response = bookingService.bookTicket(bookingRequestDto);
		// Call Inventory through Kafka to book tickets
		kafkaTemplate.send("BookingToInventoryBook", JsonUtil.toJson(bookingRequestDto));
//		return "Ticket booked successfully Your PNR number is " + response.getPnr();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<BookingResponseDto> findAll(@RequestParam(value = "search") String search) {
		return bookingService.getQueryResult(SearchUtility.searchFilter(search), search);
	}

	@DeleteMapping
	public ResponseEntity<BookingResponseDto> cancelTicket(@RequestParam String pnr) {
		BookingResponseDto bookResponseDto = bookingService.cancelTicket(pnr);
		// Call Inventory through Kafka to book tickets
		kafkaTemplate.send("BookingToInventoryCancel", JsonUtil.toJson(bookResponseDto));
		return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);

	}

	/**
	 * To get all flights. user can filter the search using this string in url.
	 * ?search=source:3,destination:2,scheduledfor~monday
	 * 
	 * @return
	 */
	@GetMapping("/flight")
	public CommonResponse[] getAllFlights(@RequestParam(value = "search") String search, @RequestParam(value="departureDate") String date) {
		return webClientBuilder.build().get()
				.uri("http://flight-service/api/flight", uriBuilder -> uriBuilder.queryParam("search", search).queryParam("departureDate", date).build())
				.retrieve().bodyToMono(CommonResponse[].class).block();
	}

//	/**
//	 * To get flightById.
//	 * 
//	 * @return
//	 */
//	@GetMapping("/flight/{id}")
//	public CommonResponse getFlightById(@PathVariable Long id) {
//		return webClientBuilder.build().get()
//				.uri("http://flight-service/api/flight", uriBuilder -> uriBuilder.queryParam("id", id).build())
//				.retrieve().bodyToMono(CommonResponse.class).block();
//	}
	
	/**
	 * To get Airport List. ?search=status:1
	 * 
	 * @param search
	 * @return
	 */
	@GetMapping("/airport")
	public CommonResponse[] getAllAirports(@RequestParam(value = "search") String search) {
		return webClientBuilder.build().get()
				.uri("http://flight-service/api/airport", uriBuilder -> uriBuilder.queryParam("search", search).build())
				.retrieve().bodyToMono(CommonResponse[].class).block();
	}

	/**
	 * To get Airline List. ?search=status:1
	 * 
	 * @param search
	 * @return
	 */
	@GetMapping("/airline")
	public CommonResponse[] getAllAirlines(@RequestParam(value = "search") String search) {
		return webClientBuilder.build().get()
				.uri("http://flight-service/api/airline", uriBuilder -> uriBuilder.queryParam("search", search).build())
				.retrieve().bodyToMono(CommonResponse[].class).block();

	}

}
