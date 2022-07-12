package com.flightapp.bookingservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flightapp.commonmodule.constants.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BookingRequestDto {
	private String pnr;
	private String email;
	private Long flightId;
	@JsonProperty("passengerCount")
	private Integer noOfSeats;
	private LocalDateTime bookingDate;
	private String departureDate;
	@JsonProperty("passengers")
	private List<PassengerDetailsDto> passengerDetailsDtoList;
	private Integer status;
	
	public LocalDate getDepartureDateAsDate(String departureDate) {
		return LocalDate.parse(departureDate,Constants.DATE_FORMATTER);
	}


}
