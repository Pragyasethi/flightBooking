package com.flightapp.flightservice.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AirlineRequest {
	private String id;
	@NotBlank
	private String airlineName;
	@NotBlank
	private String airlineLogo;
	private Integer status;

}
