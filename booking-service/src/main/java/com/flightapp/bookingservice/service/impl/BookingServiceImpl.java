package com.flightapp.bookingservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;

	private final WebClient webClient;
	
	private final SpecificationBuilder<Booking> specificationBuilder;

	@Override
	public String bookTicket(BookingRequestDto bookingRequestDto) {
		Booking bookingDetails = mapToModel(bookingRequestDto);
		bookingDetails.setPnrNumber(UUID.randomUUID().toString());
		bookingDetails.setBookingDate(LocalDateTime.now());

		// to convert Dto to model
		List<PassengerDetails> passengerDetailsList = bookingRequestDto.getPassengerDetailsDtoList().stream()
				.map(this::mapToModelPassengerDetails).collect(Collectors.toList());

		bookingDetails.setPassengerDetails(passengerDetailsList);

		// Call Inventory Service to check Seats availability at the time of booking.

		MultiValueMap<String, String> requestParamMap =new LinkedMultiValueMap<>();
		requestParamMap.add("flightId", String.valueOf(bookingRequestDto.getFlightId()));
		requestParamMap.add("seat", String.valueOf(bookingRequestDto.getNoOfSeats()));

		Boolean isFlightWithCapacityExists = webClient.get().uri("http://inventory-service/api/inventory",uriBuilder ->uriBuilder.queryParams(requestParamMap).build() )
				.retrieve()
				.bodyToMono(Boolean.class).block();
		if (Boolean.TRUE.equals(isFlightWithCapacityExists)) {
			return bookingRepository.save(bookingDetails).getPnrNumber();
		} else {
			throw new IllegalArgumentException();
		}
	}

	private PassengerDetails mapToModelPassengerDetails(PassengerDetailsDto passengerDetailsDto) {
		return PassengerDetails.builder().passengerName(passengerDetailsDto.getPassengerName())
				.gender(passengerDetailsDto.getGender()).age(passengerDetailsDto.getAge())
				.idProofNumber(passengerDetailsDto.getGender()).build();
	}

	private Booking mapToModel(BookingRequestDto bookingRequestDto) {
		return Booking.builder().email(bookingRequestDto.getEmail()).flightId(bookingRequestDto.getFlightId()).build();
	}

	@Override
	public List<BookingResponseDto> getQueryResult(List<SearchCriteria> criteriaFilter) {
		List<Booking> bookingDetailsList;
		if (criteriaFilter.isEmpty()) {
			bookingDetailsList = bookingRepository.findAll();
		} else {
			bookingDetailsList = bookingRepository
					.findAll(specificationBuilder.getSpecificationFromFilters(criteriaFilter));
		}
		return bookingDetailsList.stream().map(this::mapToDtoResponse).collect(Collectors.toList());

	}
	
	private BookingResponseDto mapToDtoResponse(Booking booking) {
		return BookingResponseDto.builder().id(booking.getId())
				.pnrNumber(booking.getPnrNumber())
				.email(booking.getEmail())
				.flightId(booking.getFlightId())
				.noOfSeats(booking.getNoOfSeats())
				.bookingDate(booking.getBookingDate())
				.status(StatusEnum.fromStatus(booking.getStatus()).getStatusName()).build();
	}

}
