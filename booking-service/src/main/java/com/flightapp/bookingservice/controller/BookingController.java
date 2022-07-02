package com.flightapp.bookingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.commonmodule.utility.SearchUtility;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {
	
	private final BookingService bookingService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
		String pnr = bookingService.bookTicket(bookingRequestDto);
		//Call Inventory through Kafka to book tickets
		
		return "Ticket booked successfully Your PNR number is "+pnr;
		
	}
	
	@GetMapping("/ticket")
	@ResponseStatus(HttpStatus.OK)
	public List<BookingResponseDto> findAll(@RequestParam(value = "search") String search) {
		return bookingService.getQueryResult(SearchUtility.searchFilter(search));	
	}

}
