package com.flightapp.inventoryservice.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.inventoryservice.dto.InventoryResponse;
import com.flightapp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin

public class InventoryController {

	private final InventoryService inventoryService;

//	@GetMapping
//	public boolean checkAvailability(@RequestParam("flightId") String flightId, @RequestParam("seat") String seat) {
//		return inventoryService.checkSeatAvailability(flightId, Integer.parseInt(seat));
//
//	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> fetchFlightInventory(@RequestParam("flightId") List<Long> flightIds,
			@RequestParam("departureDate") String date) throws ParseException {
		return inventoryService.fetchFlightInventory(flightIds, date);
	}
}
