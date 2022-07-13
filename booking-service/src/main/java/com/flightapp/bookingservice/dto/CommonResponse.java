package com.flightapp.bookingservice.dto;

import java.time.LocalTime;

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
public class CommonResponse {
	private Long flightId;
	private String flightNumber;
	private Double price;
	private String airlineId;
	private String capacity;
	private String status;
	private String airlineName;
	private String airlineLogo;
	private String source;
	private String destination;
	@JsonProperty("departureTime")
	private LocalTime deptTime;
	@JsonProperty("arrivalTime")
	private LocalTime arrTime;
	private String scheduledfor;
	private String airportId;
	private String airportCode;
	private String airportLocation;
	private String airportName;
	private Integer availableSeats;


}
