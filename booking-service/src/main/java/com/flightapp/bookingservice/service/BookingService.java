package com.flightapp.bookingservice.service;

import java.util.List;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.commonmodule.model.SearchCriteria;

public interface BookingService {
	
	/**
	 * Book the tickets with the provided information.
	 * @param bookingRequestDto
	 * @return
	 */
	public String bookTicket(BookingRequestDto bookingRequestDto);

	/**
	 * Find all Ticket Details.
	 * Find ticket by filters.
	 * @param pnr
	 * @return
	 */
	public List<BookingResponseDto> getQueryResult(List<SearchCriteria> criteriaFilter);


}
