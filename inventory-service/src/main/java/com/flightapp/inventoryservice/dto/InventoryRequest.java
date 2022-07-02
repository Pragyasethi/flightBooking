package com.flightapp.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class InventoryRequest {

	private Long id;
	private String flightId;
	private String flightNumber;
	private String capacity;
	private Integer status;

}
