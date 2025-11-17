package com.flightapp.service;

import com.flightapp.dto.request.FlightCreateRequest;
import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.model.Flight;

import java.util.List;

public interface FlightService {

    Flight addInventory(FlightCreateRequest request);

    List<FlightSearchResponse> searchFlights(FlightSearchRequest request);

    Flight getFlightById(Long id);
}
