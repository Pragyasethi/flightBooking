package com.flightapp.inventoryservice.service;

import com.flightapp.inventoryservice.dto.InventoryRequest;

public interface InventoryService {

	/**
	 * To check the Seats available in particular flight before booking.
	 * Generally will be called by Booking Service too.
	 * @param flightId
	 * @param seats
	 * @return
	 */
	public boolean checkSeatAvailability(String flightId,Integer seats);
	
	/**
	 * To save data consumed by consumer.
	 * @param inventoryRequest
	 */
	public void saveInventoryDetails(InventoryRequest inventoryRequest);

	/**
	 * To update flight capacity after successful booking
	 * @param request
	 */
	public void updateFlightCapacityForBooking(InventoryRequest request);
	
	/**
	 * To update flight capacity after Cancelling booking
	 * @param request
	 */
	public void updateFlightCapacityForCancellation(InventoryRequest request);

}
