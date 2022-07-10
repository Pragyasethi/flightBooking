package com.flightapp.flightservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flightapp.flightservice.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>,JpaSpecificationExecutor<Flight> {

	Flight findByAirlineIdAndStatus(Long airlineId,Integer status);

	Optional<Flight> findByIdAndAirlineIdAndStatus(Long flightId, Long airlineId,Integer status);
	
	List<Flight> findAllBySourceOrDestination(String source, String destination);

	@Query("from Flight f left join fetch f.airline a where a.id =?1")
	List<Flight> findAllByAirlineId(Long airlineId);
	
//	@Query("from Flight f left join fetch f.flightSchedule s where s.source =?1")
//	List<Flight> searchFlightBasedOnFilter(String source);

}
