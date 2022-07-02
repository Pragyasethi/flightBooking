package com.flightapp.bookingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.flightapp.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>,JpaSpecificationExecutor<Booking>{
	
	Optional<Booking> findByPnrNumber(String pnrNumber);
	
}
