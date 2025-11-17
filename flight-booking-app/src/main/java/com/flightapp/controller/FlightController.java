package com.flightapp.controller;

import com.flightapp.dto.request.FlightCreateRequest;
import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.model.Flight;
import com.flightapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/airline/inventory/add")
    public Flight addInventory(@RequestBody @Valid FlightCreateRequest request) {
        return flightService.addInventory(request);
    }

    @PostMapping("/search")
    public List<FlightSearchResponse> searchFlights(@RequestBody @Valid FlightSearchRequest request) {
        return flightService.searchFlights(request);
    }
}
