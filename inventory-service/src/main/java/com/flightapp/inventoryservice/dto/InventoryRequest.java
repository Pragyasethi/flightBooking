package com.flightapp.inventoryservice.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flightapp.inventoryservice.constants.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class InventoryRequest {

	private Long id;
	private Long flightId;
	@JsonProperty("noOfSeats")
	@JsonAlias("noOfSeats")
	private Integer noOfSeats;
	private String departureDate;

	public LocalDate getDepartureDateAsDate(String departureDate) {
		return LocalDate.parse(departureDate,Constants.DATE_FORMATTER);
	}

}
