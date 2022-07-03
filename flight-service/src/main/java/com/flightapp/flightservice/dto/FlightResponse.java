package com.flightapp.flightservice.dto;

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
public class FlightResponse {
	private String flightId;
	private String flightNumber;
	private Double price;
	private String airlineId;
	private String capacity;
	private String status;
	private String airlineName;
	private String airlineLogo;

}
