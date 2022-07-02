package com.flightapp.bookingservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	private String pnrNumber;
	private String email;
	private Long flightId;
	private String flightNumber;
	private String fromCity;
	private String toCity;
	private LocalDate departureDate;
	private Integer noOfSeats;
	private LocalDateTime bookingDate;
	private List<PassengerDetailsDto> passengerDetailsDtoList;
	private String status;


}
