package com.flightapp.bookingservice.dto;

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
public class PassengerDetailsDto {
	private Long passengerId;
	private String passengerName;
	private String gender;
	private Integer age;
	private String idProofNumber;
}
