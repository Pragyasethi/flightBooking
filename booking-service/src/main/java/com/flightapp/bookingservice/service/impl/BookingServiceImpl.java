package com.flightapp.bookingservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightapp.bookingservice.dto.BookingRequestDto;
import com.flightapp.bookingservice.dto.BookingResponseDto;
import com.flightapp.bookingservice.dto.CommonResponse;
import com.flightapp.bookingservice.dto.PassengerDetailsDto;
import com.flightapp.bookingservice.model.Booking;
import com.flightapp.bookingservice.model.PassengerDetails;
import com.flightapp.bookingservice.repository.BookingRepository;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.commonmodule.constants.Constants;
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
	public BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto) {
		Booking bookingDetails = mapToModel(bookingRequestDto);
		bookingDetails.setPnr(UUID.randomUUID().toString());
		bookingDetails.setBookingDate(LocalDateTime.now());

		// to convert Dto to model
		List<PassengerDetails> passengerDetailsList = bookingRequestDto.getPassengerDetailsDtoList().stream()
				.map(this::mapToModelPassengerDetails).collect(Collectors.toList());

		bookingDetails.setPassengerDetails(passengerDetailsList);

		CommonResponse[] flightResponse = fetchFlightDetails(bookingRequestDto.getFlightId(),
				bookingRequestDto.getDepartureDate());

		if (flightResponse != null && flightResponse[0] != null
				&& flightResponse[0].getAvailableSeats() >= bookingRequestDto.getNoOfSeats()) {
			return mapToDtoResponse(bookingRepository.save(bookingDetails), flightResponse[0]);
		} else {
			throw new ResourceNotFoundException(
					"Seats are not available for FlightId : " + bookingRequestDto.getFlightId());
		}
	}

	private PassengerDetails mapToModelPassengerDetails(PassengerDetailsDto passengerDetailsDto) {
		return PassengerDetails.builder().passengerName(passengerDetailsDto.getPassengerName())
				.gender(passengerDetailsDto.getGender()).age(passengerDetailsDto.getAge())
				.idProofNumber(passengerDetailsDto.getIdProofNumber()).build();
	}

	private PassengerDetailsDto mapToModelPassengerDetailsDto(PassengerDetails passengerDetails) {
		return PassengerDetailsDto.builder().passengerId(passengerDetails.getId()).passengerName(passengerDetails.getPassengerName())
				.gender(passengerDetails.getGender()).age(passengerDetails.getAge())
				.idProofNumber(passengerDetails.getGender()).idProofNumber(passengerDetails.getIdProofNumber()).build();
	}

	private Booking mapToModel(BookingRequestDto bookingRequestDto) {
		return Booking.builder().email(bookingRequestDto.getEmail()).flightId(bookingRequestDto.getFlightId())
				.noOfSeats(bookingRequestDto.getNoOfSeats())
				.departureDate(bookingRequestDto.getDepartureDateAsDate(bookingRequestDto.getDepartureDate())).build();
	}

	@Override
	public List<BookingResponseDto> getQueryResult(List<SearchCriteria> criteriaFilter, String search) {
		List<Booking> bookingDetailsList = new ArrayList<>();
		if (criteriaFilter.isEmpty() && ObjectUtils.isEmpty(search)) {
			bookingDetailsList = bookingRepository.findAll();
		} else if (!criteriaFilter.isEmpty()) {
			bookingDetailsList = bookingRepository
					.findAll(specificationBuilder.getSpecificationFromFilters(criteriaFilter));
		}
		List<Long> flightIds = bookingDetailsList.stream().map(Booking::getFlightId).collect(Collectors.toList());
		
		CommonResponse[] flightResponse = fetchFlightDetails(flightIds,"");
		List<BookingResponseDto> bookingResponseDto = bookingDetailsList.stream().map(this::mapToDtoResponse).collect(Collectors.toList());
		if (flightResponse != null) {
			for(CommonResponse response:flightResponse) {
				for(BookingResponseDto dtoResponse:bookingResponseDto) {
					if(response.getFlightId().compareTo(dtoResponse.getFlightId())==0) {
						dtoResponse.setFlightNumber(response.getFlightNumber());
						dtoResponse.setFromCity(response.getSource());
						dtoResponse.setToCity(response.getDestination());
					}
				}
				
			}

		}
		return bookingResponseDto;

	}

	private BookingResponseDto mapToDtoResponse(Booking booking, CommonResponse commonResponse) {
		List<PassengerDetailsDto> passengerDetailsDtoList = booking.getPassengerDetails().stream()
				.map(this::mapToModelPassengerDetailsDto).collect(Collectors.toList());
		return BookingResponseDto.builder().id(booking.getId()).pnr(booking.getPnr()).email(booking.getEmail())
				.flightId(booking.getFlightId()).noOfSeats(booking.getNoOfSeats()).bookingDate(booking.getBookingDate())
				.departureDate(booking.getDepartureDate().format(Constants.DATE_FORMATTER))
				.passengerDetailsDtoList(passengerDetailsDtoList)
				.status(StatusEnum.fromStatus(booking.getStatus()).getStatusName())
				.flightNumber(commonResponse.getFlightNumber()).fromCity(commonResponse.getSource())
				.toCity(commonResponse.getDestination()).build();
	}

	private BookingResponseDto mapToDtoResponse(Booking booking) {
		List<PassengerDetailsDto> passengerDetailsDtoList = booking.getPassengerDetails().stream()
				.map(this::mapToModelPassengerDetailsDto).collect(Collectors.toList());
		return BookingResponseDto.builder().id(booking.getId()).pnr(booking.getPnr()).email(booking.getEmail())
				.flightId(booking.getFlightId()).noOfSeats(booking.getNoOfSeats()).bookingDate(booking.getBookingDate())
				.departureDate(booking.getDepartureDate().format(Constants.DATE_FORMATTER))
				.passengerDetailsDtoList(passengerDetailsDtoList)
				.status(StatusEnum.fromStatus(booking.getStatus()).getStatusName()).build();
	}

	@Override
	public BookingResponseDto cancelTicket(String pnr) {

		Booking booking = bookingRepository.findByPnr(pnr)
				.orElseThrow(() -> new ResourceNotFoundException("Booking Details not found for pnr:  " + pnr));
		booking.setStatus(StatusEnum.CANCELLED.getStatus());
		bookingRepository.save(booking);
		CommonResponse[] flightResponse = fetchFlightDetails(booking.getFlightId(),
				booking.getDepartureDate().format(Constants.DATE_FORMATTER));
		if (flightResponse != null && flightResponse[0] != null) {
			return mapToDtoResponse(booking, flightResponse[0]);
		} else {
			throw new ResourceNotFoundException("Seats are not available for FlightId : " + booking.getFlightId());
		}

	}

	/**
	 * To fetch flight details from Flightservice.
	 * 
	 * @param flightId
	 * @param departureDate
	 * @return
	 */
	private CommonResponse[] fetchFlightDetails(Long flightId, String departureDate) {
		return webClientBuilder.build().get()
				.uri("http://flight-service/api/flight",
						uriBuilder -> uriBuilder.queryParam("search", "id:" + flightId)
								.queryParam("departureDate", departureDate).build())
				.retrieve().bodyToMono(CommonResponse[].class).block();
	}

	/**
	 * To fetch flight details from Flightservice.
	 * 
	 * @param flightIds
	 * @return
	 */
	private CommonResponse[] fetchFlightDetails(List<Long> flightIds,String departureDate) {
		String searchString = "";
		for (Long flightId : flightIds) {
			searchString = searchString.concat("id:").concat(flightId.toString()).concat(",");
		}
		final String str = searchString;
		return webClientBuilder.build().get()
				.uri("http://flight-service/api/flight", uriBuilder -> uriBuilder.queryParam("departureDate", departureDate).queryParam("search", str).build())
				.retrieve().bodyToMono(CommonResponse[].class).block();
	}

}
