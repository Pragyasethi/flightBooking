package com.flightapp.flightservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.flightapp.flightservice.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>,JpaSpecificationExecutor<Flight> {

	Flight findByAirlineId(Long airlineId);

	Optional<Flight> findByIdAndAirlineId(Long flightId, Long airlineId);

}
