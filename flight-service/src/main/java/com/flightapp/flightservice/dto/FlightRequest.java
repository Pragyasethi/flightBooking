package com.flightapp.flightservice.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class FlightRequest {
	@JsonAlias("flightId")
	private String id;
	@NotBlank
	private String flightNumber;
	@NotNull
	private Double price;
	@NotBlank
	private String airlineId;
	@NotBlank
	private String capacity;
	private Integer status;
	private String departureDate;
//	@JsonProperty("schedule")
//	private FlightScheduleDto flightScheduleDto;
	
	private String source;
	private String destination;
	@JsonAlias("departureTime")
	private LocalTime deptTime;
	@JsonAlias("arrivalTime")
	private LocalTime arrTime;
	private String scheduledfor;
	
	public Long getAirlineIdAsLong() {
		return Long.valueOf(this.airlineId);
	}

	public Long getIdAsLong() {
		return Long.valueOf(this.id);
	}

}
