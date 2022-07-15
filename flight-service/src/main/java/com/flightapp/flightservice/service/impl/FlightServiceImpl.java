package com.flightapp.flightservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.commonmodule.specifications.SpecificationBuilder;
import com.flightapp.flightservice.dto.AirportRequest;
import com.flightapp.flightservice.dto.FlightRequest;
import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.dto.InventoryResponse;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;
import com.flightapp.flightservice.model.Airline;
import com.flightapp.flightservice.model.Airport;
import com.flightapp.flightservice.model.Flight;
import com.flightapp.flightservice.repository.AirlineRepository;
import com.flightapp.flightservice.repository.AirportRepository;
import com.flightapp.flightservice.repository.FlightRepository;
import com.flightapp.flightservice.service.FlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

	private final FlightRepository flightRepository;

	private final AirlineRepository airlineRepository;

	private final AirportRepository airportRepository;

	private final SpecificationBuilder<Flight> specificationBuilder;

	private final WebClient.Builder webClientBuilder;

	private FlightResponse mapToDto(Flight flight) {
//		FlightScheduleDto flightScheduleDto = FlightScheduleDto.builder()
//				.arrTime(flight.getFlightSchedule().getArrTime())
//				.deptTime(flight.getFlightSchedule().getDeptTime())
//				.source(flight.getFlightSchedule().getSource().getAirportCode())
//				.destination(flight.getFlightSchedule().getDestination().getAirportCode())
//				.scheduledfor(flight.getFlightSchedule().getScheduledfor())
//				.build();

		Airport source = airportRepository.findById(Long.valueOf(flight.getSource()))
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airport " + flight.getSource() + " Not found to add Flight Details "));

		Airport destination = airportRepository.findById(Long.valueOf(flight.getDestination()))
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airport " + flight.getDestination() + " Not found to add Flight Details "));
		return FlightResponse.builder().flightId(flight.getId().toString()).flightNumber(flight.getFlightNumber())
				.price(flight.getPrice()).status(StatusEnum.fromStatus(flight.getStatus()).getStatusName())
				.airlineId(flight.getAirline().getId().toString()).airlineLogo(flight.getAirline().getAirlineLogo())
				.airlineName(flight.getAirline().getAirlineName()).capacity(flight.getCapacity())
				.arrTime(flight.getArrTime()).deptTime(flight.getDeptTime())
//				.source(flight.getSource().getAirportCode())
//				.destination(flight.getDestination().getAirportCode())
				.source(source.getAirportLocation()).destination(destination.getAirportLocation())

				.scheduledfor(flight.getScheduledfor()).availableSeats(flight.getCapacity())
				// .flightScheduleDto(flightScheduleDto)
				.build();
	}

	private Flight mapToModel(FlightRequest flightRequest) {
//		Airport source = airportRepository.findById(Long.valueOf(flightRequest.getSource()))
//				.orElseThrow(() -> new ResourceNotFoundException("Airport "
//						+ flightRequest.getSource() + " Not found to add Flight Details "));
//
//		Airport destination = airportRepository.findById(Long.valueOf(flightRequest.getDestination()))
//				.orElseThrow(() -> new ResourceNotFoundException("Airport "
//						+ flightRequest.getDestination() + " Not found to add Flight Details "));

//		FlightSchedule flightSchedule = FlightSchedule.builder()
//				.arrTime(flightRequest.getFlightScheduleDto().getArrTime())
//				.deptTime(flightRequest.getFlightScheduleDto().getDeptTime()).source(source).destination(destination)
//				.scheduledfor(flightRequest.getFlightScheduleDto().getScheduledfor()).build();
//		flightScheduleRepository.save(flightSchedule);

		return Flight.builder().flightNumber(flightRequest.getFlightNumber()).price(flightRequest.getPrice())
				.capacity(Integer.parseInt(flightRequest.getCapacity())).status(StatusEnum.ACTIVE.getStatus())
				.arrTime(flightRequest.getArrTime()).deptTime(flightRequest.getDeptTime())
				.source(flightRequest.getSource()).destination(flightRequest.getDestination())
				.scheduledfor(flightRequest.getScheduledfor()).build();
	}

	@Override
	@Transactional
	public FlightResponse addNewFlightDetails(FlightRequest flightRequest) throws ResourceNotFoundException {
		Flight flight = mapToModel(flightRequest);

		Airline airline = airlineRepository.findById(flightRequest.getAirlineIdAsLong())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airline with id " + flightRequest.getAirlineId() + " Not found to add Flight Details "));
		flight.setAirline(airline);
		return mapToDto(flightRepository.save(flight));

	}

	@Override
	public List<FlightResponse> findAll() {
		List<Flight> flights = flightRepository.findAll();
		return flights.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public Flight getAllByAirlineId(Long airlineId) {
		return flightRepository.findByAirlineIdAndStatus(airlineId, StatusEnum.ACTIVE.getStatus());
	}

	@Override
	public FlightResponse updateFlightDetails(FlightRequest flightRequest) {
		if (!airlineRepository.existsById(flightRequest.getAirlineIdAsLong())) {
			throw new ResourceNotFoundException("Airline with id " + flightRequest.getAirlineId() + " not found");
		}

		Flight fetchedFlight = flightRepository.findById(flightRequest.getIdAsLong()).orElseThrow(
				() -> new ResourceNotFoundException("Flight Details not found for id: " + flightRequest.getId()));
		Airline airline = airlineRepository.findById(flightRequest.getAirlineIdAsLong())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Airline with id " + flightRequest.getAirlineId() + " Not found to add Flight Details "));
		fetchedFlight.setAirline(airline);
		flightRepository.save(mapToModel(flightRequest,fetchedFlight));
		return mapToDto(fetchedFlight);
	}

	private Flight mapToModel(FlightRequest flightRequest, Flight flight) {
		flight.setFlightNumber(flightRequest.getFlightNumber());
		flight.setPrice(flightRequest.getPrice());
		flight.setCapacity(Integer.parseInt(flightRequest.getCapacity()));
		flight.setStatus(flight.getStatus());
		flight.setArrTime(flightRequest.getArrTime());
		flight.setDeptTime(flightRequest.getDeptTime());
		flight.setSource(flightRequest.getSource());
		flight.setDestination(flightRequest.getDestination());
		flight.setScheduledfor(flightRequest.getScheduledfor());
		return flight;
	}

	@Override
	public ResponseEntity<?> deleteFlightByIdAndAirlineId(Long flightId, Long airlineId) {
		return flightRepository.findByIdAndAirlineIdAndStatus(flightId, airlineId, StatusEnum.ACTIVE.getStatus())
				.map(flight -> {
					flightRepository.delete(flight);
					return ResponseEntity.ok("Flight Details deleted for Flight Number : " + flight.getFlightNumber());
				}).orElseThrow(() -> new ResourceNotFoundException("Flight Details not found for id: " + flightId));
	}

	@Override
	public List<FlightResponse> getQueryResult(List<SearchCriteria> searchFilter, String date) {
		List<Flight> flightsList;
		if (searchFilter.isEmpty()) {
			flightsList = flightRepository.findAll();
		} else {
			flightsList = flightRepository.findAll(specificationBuilder.getSpecificationFromFilters(searchFilter));
		}
		List<FlightResponse> flightResponseList = flightsList.stream().map(this::mapToDto).collect(Collectors.toList());
		if (!ObjectUtils.isEmpty(date)) {
			List<Long> flightIds = flightsList.stream().map(Flight::getId).collect(Collectors.toList());
			InventoryResponse[] inventoryResponseList = webClientBuilder.build().get()
					.uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder
							.queryParam("departureDate", date).queryParam("flightId", flightIds).build())
					.retrieve().bodyToMono(InventoryResponse[].class).block();

			if (inventoryResponseList != null && inventoryResponseList.length > 0) {
				for (FlightResponse flightResponse : flightResponseList) {
					for (InventoryResponse inventoryResponse : inventoryResponseList) {
						if (Objects.equals(flightResponse.getFlightId(), inventoryResponse.getFlightId())) {
							flightResponse.setAvailableSeats(
									flightResponse.getCapacity() - inventoryResponse.getBookedSeats());
							break;
						}
					}

				}
			}
//			return flightResponseList.stream()
//					.filter(flightResponse -> Arrays.stream(inventoryResponseList)
//							.allMatch(inventoryResponse -> 
//								Objects.equals(inventoryResponse.getFlightId(), flightResponse.getFlightId()))
//									.collect(Collectors.toList());
		}

		return flightResponseList;

	}

	@Override
	public ResponseEntity<String> deleteFlight(Long id) {
		return flightRepository.findById(id).map(flight -> {
			flightRepository.delete(flight);
			return ResponseEntity.ok("Flight Details deleted for Flight Number: " + flight.getFlightNumber());
		}).orElseThrow(() -> new ResourceNotFoundException("Flight Details not found for id: " + id));
	}

	@Override
	public FlightResponse findById(Long id) {
		Flight flight = flightRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Airport Id " + id + " Not found "));
		return mapToDto(flight);
	}

	@Override
	public void updateStatus(Integer status, String airportId, String airlineId) {
		List<Flight> flightList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(airportId)) {
			flightList = flightRepository.findAllBySourceOrDestination(airportId, airportId);
		}
		if (!ObjectUtils.isEmpty(airlineId)) {
			flightList = flightRepository.findAllByAirlineId(Long.valueOf(airlineId));
		}
		flightList.forEach(flight -> flight.setStatus(status));
		flightRepository.saveAll(flightList);
	}

}
