package com.flightapp.bookingservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.bookingservice.dto.PassengerDetailsDto;
import com.flightapp.bookingservice.model.Booking;
import com.flightapp.bookingservice.model.PassengerDetails;
import com.flightapp.bookingservice.repository.BookingRepository;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.commonmodule.constants.StatusEnum;
import com.flightapp.commonmodule.model.SearchCriteria;
import com.flightapp.commonmodule.specifications.SpecificationBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;

	private final WebClient.Builder webClientBuilder;

	private final SpecificationBuilder<Booking> specificationBuilder;

	@Override
	public String bookTicket(BookingRequestDto bookingRequestDto) {
		Booking bookingDetails = mapToModel(bookingRequestDto);
		bookingDetails.setPnr(UUID.randomUUID().toString());
		bookingDetails.setBookingDate(LocalDateTime.now());

		// to convert Dto to model
		List<PassengerDetails> passengerDetailsList = bookingRequestDto.getPassengerDetailsDtoList().stream()
				.map(this::mapToModelPassengerDetails).collect(Collectors.toList());

		bookingDetails.setPassengerDetails(passengerDetailsList);

		// Call Inventory Service to check Seats availability at the time of booking.

		MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap<>();
		requestParamMap.add("flightId", String.valueOf(bookingRequestDto.getFlightId()));
		requestParamMap.add("seat", String.valueOf(bookingRequestDto.getNoOfSeats()));
		Boolean isFlightWithCapacityExists = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParams(requestParamMap).build())
				.retrieve().bodyToMono(Boolean.class).block();
		if (Boolean.TRUE.equals(isFlightWithCapacityExists)) {
			return bookingRepository.save(bookingDetails).getPnr();
		} else {
			throw new ResourceNotFoundException("Seats are not available for FlightId : " + bookingRequestDto.getFlightId());
		}
	}

	private PassengerDetails mapToModelPassengerDetails(PassengerDetailsDto passengerDetailsDto) {
		return PassengerDetails.builder().passengerName(passengerDetailsDto.getPassengerName())
				.gender(passengerDetailsDto.getGender()).age(passengerDetailsDto.getAge())
				.idProofNumber(passengerDetailsDto.getGender()).build();
	}

	private PassengerDetailsDto mapToModelPassengerDetailsDto(PassengerDetails passengerDetails) {
		return PassengerDetailsDto.builder().passengerName(passengerDetails.getPassengerName())
				.gender(passengerDetails.getGender()).age(passengerDetails.getAge())
				.idProofNumber(passengerDetails.getGender()).build();
	}

	private Booking mapToModel(BookingRequestDto bookingRequestDto) {
		return Booking.builder().email(bookingRequestDto.getEmail()).flightId(bookingRequestDto.getFlightId())
				.noOfSeats(bookingRequestDto.getNoOfSeats()).build();
	}

	@Override
	public List<BookingResponseDto> getQueryResult(List<SearchCriteria> criteriaFilter,String search) {
		List<Booking> bookingDetailsList = new ArrayList<>();
		if (criteriaFilter.isEmpty() && ObjectUtils.isEmpty(search)) {
			bookingDetailsList = bookingRepository.findAll();
		} else if(!criteriaFilter.isEmpty()) {
			bookingDetailsList = bookingRepository
					.findAll(specificationBuilder.getSpecificationFromFilters(criteriaFilter));
		}
		return bookingDetailsList.stream().map(this::mapToDtoResponse).collect(Collectors.toList());

	}

	private BookingResponseDto mapToDtoResponse(Booking booking) {
		List<PassengerDetailsDto> passengerDetailsDtoList = booking.getPassengerDetails().stream()
				.map(this::mapToModelPassengerDetailsDto).collect(Collectors.toList());
		return BookingResponseDto.builder().id(booking.getId()).pnr(booking.getPnr()).email(booking.getEmail())
				.flightId(booking.getFlightId()).noOfSeats(booking.getNoOfSeats()).bookingDate(booking.getBookingDate())
				.passengerDetailsDtoList(passengerDetailsDtoList)
				.status(StatusEnum.fromStatus(booking.getStatus()).getStatusName()).build();
	}

	@Override
	public BookingResponseDto cancelTicket(String pnr) {

		Booking booking =bookingRepository.findByPnr(pnr).orElseThrow(() -> new ResourceNotFoundException(
				"Booking Details not found for pnr:  " + pnr ));
		booking.setStatus(StatusEnum.CANCELLED.getStatus());
		bookingRepository.save(booking);
		return mapToDtoResponse(booking);
	}

}
