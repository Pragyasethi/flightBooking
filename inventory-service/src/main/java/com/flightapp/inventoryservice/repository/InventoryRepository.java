package com.flightapp.inventoryservice.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.inventoryservice.dto.InventoryResponse;
import com.flightapp.inventoryservice.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findByFlightIdAndCapacityGreaterThanEqual(String flightId,Integer seats);
	
	Optional<Inventory> findByFlightId(String flightId);

	List<InventoryResponse> findByFlightIdInAndDate(List<Long> flightIds, Date sqlDate);


}
