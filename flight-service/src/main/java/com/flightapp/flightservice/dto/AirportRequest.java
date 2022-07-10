package com.flightapp.flightservice.dto;

import javax.validation.constraints.NotBlank;

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
public class AirportRequest {
	private String id;
	@NotBlank
	private String airportCode;
	@NotBlank
	private String airportLocation;
	@NotBlank
	private String airportName;
	private Integer status;

	public Long getIdAsLong() {
		return Long.valueOf(this.id);
	}


}
