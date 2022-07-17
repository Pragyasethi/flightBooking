package com.flightapp.bookingservice.service;

import java.util.List;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.bookingservice.utility.SearchCriteria;

public interface BookingService {
	
	/**
	 * Book the tickets with the provided information.
	 * @param bookingRequestDto
	 * @return
	 */
	public BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto);

	/**
	 * Find all Ticket Details.
	 * Find ticket by filters.
	 * @param pnr
	 * @return
	 */
	public List<BookingResponseDto> getQueryResult(List<SearchCriteria> criteriaFilter,String search);

	/**
	 * To change the status to Cancelled.
	 * @param pnr
	 * @return
	 */
	public BookingResponseDto cancelTicket(String pnr);


}
