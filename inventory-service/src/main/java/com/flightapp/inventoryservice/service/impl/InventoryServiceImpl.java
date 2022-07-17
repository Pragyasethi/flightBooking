package com.flightapp.inventoryservice.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.inventoryservice.constants.Constants;
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

	private Inventory mapToModel(InventoryRequest request) {
		return Inventory.builder().flightId(request.getFlightId()).bookedSeats(request.getNoOfSeats())
				.departureDate(request.getDepartureDateAsDate(request.getDepartureDate())).build();

	}

	private InventoryResponse mapToDto(Inventory inventory) {
		return InventoryResponse.builder().id(inventory.getId()).flightId(inventory.getFlightId()).bookedSeats(inventory.getBookedSeats())
				.build();

	}

	@Override
	public void updateSeatsAfterBooking(InventoryRequest request) {
		Inventory inventory;
		Optional<Inventory> inventoryOp = inventoryRepository.findByFlightIdAndDepartureDate(request.getFlightId(),
				request.getDepartureDateAsDate(request.getDepartureDate()));
		if (inventoryOp.isPresent()) {
			inventory = inventoryOp.get();
			inventory.setBookedSeats(inventory.getBookedSeats() + request.getNoOfSeats());
		} else {
			inventory = mapToModel(request);
		}
		inventoryRepository.save(inventory);
	}

	
	@Override
	public void updateSeatsForCancellation(InventoryRequest request) {
		Optional<Inventory> inventoryOp = inventoryRepository.findByFlightIdAndDepartureDate(request.getFlightId(), request.getDepartureDateAsDate(request.getDepartureDate()));
		if(inventoryOp.isPresent()) {
			Inventory inventory = inventoryOp.get();
			inventory.setBookedSeats(inventory.getBookedSeats()-request.getNoOfSeats());
			inventoryRepository.save(inventory);		
		}
	}

	@Override
	public List<InventoryResponse> fetchFlightInventory(List<Long> flightIds, String date) throws ParseException {
//		java.util.Date utilDate = formatter.parse(date);
//		Date sqlDate = new Date(utilDate.getTime());
		LocalDate departureDate = LocalDate.parse(date, Constants.DATE_FORMATTER);

		List<Inventory> inventoryList = inventoryRepository.findByFlightIdInAndDepartureDate(flightIds, departureDate);
		return inventoryList.stream().map(this::mapToDto).collect(Collectors.toList());
	}

}
