//package com.flightapp.flightservice.dto;
//
//import java.time.LocalTime;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@JsonInclude(value = Include.NON_NULL)
//@Builder
//public class FlightScheduleDto {
//	private Long flightScheduleId;
//	private String source;
//	private String destination;
//	@JsonProperty("departureTime")
//	private LocalTime deptTime;
//	@JsonProperty("arrivalTime")
//	private LocalTime arrTime;
//	private String scheduledfor;
//
//}
