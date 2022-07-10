package com.flightapp.inventoryservice.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.commonmodule.exceptions.ResourceNotFoundException;
import com.flightapp.inventoryservice.dto.InventoryRequest;
import com.flightapp.inventoryservice.dto.InventoryResponse;
import com.flightapp.inventoryservice.model.Inventory;
import com.flightapp.inventoryservice.repository.InventoryRepository;
import com.flightapp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	private final InventoryRepository inventoryRepository;

//	@Override
//	@Transactional(readOnly = true)
//	public boolean checkSeatAvailability(String flightId,Integer seats) {
//		return inventoryRepository.findByFlightIdAndStatusAnd CapacityGreaterThanEqual(flightId, StatusEnum.ACTIVE.getStatus(),seats).isPresent();
//			
//	}
//
//	@Override
//	public void saveInventoryDetails(InventoryRequest inventoryRequest) {
//		Inventory inventory = Inventory.builder().flightId(inventoryRequest.getFlightId())
//				.capacity(inventoryRequest.getCapacity()).build();
//		inventoryRepository.save(inventory);
//
//	}

	@Override
	public void updateSeatsAfterBooking(InventoryRequest request) {
		Inventory inventory = inventoryRepository.findByFlightId(request.getFlightId())
				.orElseThrow(() -> new ResourceNotFoundException());
		inventory.setBookedSeats(request.getBookedSeats());
		inventoryRepository.save(inventory);
	}
//	
//	@Override
//	public void updateFlightCapacityForCancellation(InventoryRequest request) {
//		Inventory inventory = inventoryRepository.findByFlightIdAndStatus(request.getFlightId(), StatusEnum.ACTIVE.getStatus()).orElseThrow();
//		inventory.setCapacity(inventory.getCapacity()+request.getNoOfSeats());
//		inventoryRepository.save(inventory);		
//	}

	@Override
	public List<InventoryResponse> fetchFlightInventory(List<Long> flightIds, String date) throws ParseException {
		java.util.Date utilDate = formatter.parse(date);
		Date sqlDate = new Date(utilDate.getTime());
		return inventoryRepository.findByFlightIdInAndDate(flightIds, sqlDate);
	}

}
