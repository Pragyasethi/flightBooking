package com.flightapp.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.inventoryservice.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findByFlightIdAndStatusAndCapacityGreaterThanEqual(String flightId, Integer status,Integer seats);

	Optional<Inventory> findByFlightNumberAndStatus(String flightNumber, Integer status);

}
