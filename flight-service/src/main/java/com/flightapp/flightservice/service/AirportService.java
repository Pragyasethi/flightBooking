package com.flightapp.flightservice.service;

import java.util.List;

import com.flightapp.flightservice.dto.AirportResponse;

public interface AirportService {

	List<AirportResponse> findAll();


}
