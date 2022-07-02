package com.flightapp.flightservice.dto;

import java.math.BigDecimal;

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
	private BigDecimal price;
	private String airlineId;
	private String capacity;
	private String status;

}
