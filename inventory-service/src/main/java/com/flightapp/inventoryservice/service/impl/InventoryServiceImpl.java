package com.flightapp.inventoryservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.inventoryservice.dto.InventoryRequest;
import com.flightapp.inventoryservice.model.Inventory;
import com.flightapp.inventoryservice.repository.InventoryRepository;
import com.flightapp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean checkSeatAvailability(String flightId,Integer seats) {
		return inventoryRepository.findByFlightIdAndStatusAndCapacityGreaterThanEqual(flightId, StatusEnum.ACTIVE.getStatus(),seats).isPresent();
			
	}

	@Override
	public void saveInventoryDetails(InventoryRequest inventoryRequest) {
		Inventory inventory = Inventory.builder().flightId(inventoryRequest.getFlightId())
				.flightNumber(inventoryRequest.getFlightNumber()).capacity(inventoryRequest.getCapacity()).build();
		inventoryRepository.save(inventory);

	}

	@Override
	public void updateFlightCapacityForBooking(InventoryRequest request) {
		Inventory inventory = inventoryRepository.findByFlightIdAndStatus(request.getFlightId(), StatusEnum.ACTIVE.getStatus()).orElseThrow();
		inventory.setCapacity(inventory.getCapacity()-request.getNoOfSeats());
		inventoryRepository.save(inventory);		
	}
	
	@Override
	public void updateFlightCapacityForCancellation(InventoryRequest request) {
		Inventory inventory = inventoryRepository.findByFlightIdAndStatus(request.getFlightId(), StatusEnum.ACTIVE.getStatus()).orElseThrow();
		inventory.setCapacity(inventory.getCapacity()+request.getNoOfSeats());
		inventoryRepository.save(inventory);		
	}

}
