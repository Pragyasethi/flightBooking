package com.flightapp.inventoryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping()
	public boolean checkAvailability(@RequestParam("flightId") String flightId,@RequestParam("seat") String seats) {
		return inventoryService.checkSeatAvailability(flightId,Integer.parseInt(seats));
		
	}

}
