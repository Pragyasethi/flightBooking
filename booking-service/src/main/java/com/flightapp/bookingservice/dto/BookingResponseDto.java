package com.flightapp.bookingservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
public class BookingResponseDto {
	private Long id;
	private String pnr;
	private String email;
	private Long flightId;
	private String flightNumber;
	private String fromCity;
	private String toCity;
	private String departureDate;
	private Integer noOfSeats;
	private LocalDateTime bookingDate;
	@JsonProperty("passengerDetails")
	private List<PassengerDetailsDto> passengerDetailsDtoList;
	private String status;

	
	

}
