package com.flightapp.bookingservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BookingRequestDto {
	private String pnrNumber;
	private String email;
	private Long flightId;
	private Integer noOfSeats;
	private LocalDateTime bookingDate;
	private List<PassengerDetailsDto> passengerDetailsDtoList;
	private Integer status;


}
