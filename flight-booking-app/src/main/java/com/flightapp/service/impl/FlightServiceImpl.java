package com.flightapp.service.impl;

import com.flightapp.dto.request.FlightCreateRequest;
import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.model.Airline;
import com.flightapp.model.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public Flight addInventory(FlightCreateRequest req) {
        Airline airline = airlineRepository.findById(req.getAirlineId())
                .orElseThrow(() -> new RuntimeException("Airline not found"));

        Flight flight = Flight.builder()
                .airline(airline)
                .flightNumber(req.getFlightNumber())
                .origin(req.getOrigin())
                .destination(req.getDestination())
                .departDatetime(req.getDepartDatetime())
                .arriveDatetime(req.getArriveDatetime())
                .durationMin(req.getDurationMin())
                .price(req.getPrice())
                .totalSeats(req.getTotalSeats())
                .availableSeats(req.getTotalSeats())
                .build();

        return flightRepository.save(flight);
    }

    @Override
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest req) {
        LocalDate date = LocalDate.parse(req.getDate());
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        return flightRepository.searchFlights(
                        req.getOrigin(),
                        req.getDestination(),
                        start,
                        end)
                .stream()
                .map(flight -> FlightSearchResponse.builder()
                        .flightId(flight.getId())
                        .airlineName(flight.getAirline().getName())
                        .flightNumber(flight.getFlightNumber())
                        .origin(flight.getOrigin())
                        .destination(flight.getDestination())
                        .departDatetime(flight.getDepartDatetime())
                        .arriveDatetime(flight.getArriveDatetime())
                        .durationMin(flight.getDurationMin())
                        .price(flight.getPrice())
                        .availableSeats(flight.getAvailableSeats())
                        .build())
                .toList();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}
