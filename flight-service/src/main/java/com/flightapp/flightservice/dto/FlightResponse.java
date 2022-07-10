package com.flightapp.flightservice.dto;

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
public class FlightResponse {
	private String flightId;
	private String flightNumber;
	private Double price;
	private String airlineId;
	private Integer capacity;
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
	private Integer availableSeats;
	//private FlightScheduleDto flightScheduleDto;


}
