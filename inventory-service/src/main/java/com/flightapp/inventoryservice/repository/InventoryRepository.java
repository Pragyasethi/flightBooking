package com.flightapp.inventoryservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.inventoryservice.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	Optional<Inventory> findByFlightId(Long flightId);

	List<Inventory> findByFlightIdInAndDepartureDate(List<Long> flightIds, LocalDate departureDate);

	Optional<Inventory> findByFlightIdAndDepartureDate(Long flightId, LocalDate departureDateAsDate);


}
