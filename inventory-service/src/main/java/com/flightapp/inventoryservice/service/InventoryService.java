package com.flightapp.inventoryservice.service;

import java.text.ParseException;
import java.util.List;

import com.flightapp.inventoryservice.dto.InventoryRequest;
import com.flightapp.inventoryservice.dto.InventoryResponse;

public interface InventoryService {

//	/**
//	 * To check the Seats available in particular flight before booking.
//	 * Generally will be called by Booking Service too.
//	 * @param flightId
//	 * @param seats
//	 * @return
//	 */
//	public boolean checkSeatAvailability(String flightId,Integer seats);
//	
//	/**
//	 * To save data consumed by consumer.
//	 * @param inventoryRequest
//	 */
//	public void saveInventoryDetails(InventoryRequest inventoryRequest);

	/**
	 * To update flight capacity after successful booking
	 * @param request
	 */
	public void updateSeatsAfterBooking(InventoryRequest request);
	
//	/**
//	 * To update flight capacity after Cancelling booking
//	 * @param request
//	 */
//	public void updateFlightCapacityForCancellation(InventoryRequest request);

	public List<InventoryResponse> fetchFlightInventory(List<Long> flightIds, String date) throws ParseException;

}
