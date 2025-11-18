package com.flightapp.service.impl;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.request.PassengerRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.model.*;
import com.flightapp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private AppUser user;
    private Flight flight;
    private Airline airline;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new AppUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Test User");

        airline = new Airline();
        airline.setId(5L);
        airline.setName("TestAir");
        airline.setCode("TA123");

        flight = new Flight();
        flight.setId(10L);
        flight.setAvailableSeats(10);
        flight.setPrice(BigDecimal.valueOf(100));
        flight.setAirline(airline); 
    }

    @Test
    void testBookTicketSuccess() {

        PassengerRequest passenger = new PassengerRequest();
        passenger.setName("John");
        passenger.setAge(25);
        passenger.setGender("M");

        BookingRequest request = new BookingRequest();
        request.setNumSeats(2);
        request.setPassengers(List.of(passenger));

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(flightRepository.findById(10L))
                .thenReturn(Optional.of(flight));

        Booking saved = new Booking();
        saved.setId(99L);
        saved.setUser(user);
        saved.setFlight(flight);
        saved.setPnr("PNR12345");
        
        Passenger mockPassenger = new Passenger();
        mockPassenger.setName("John");

        saved.setPassengers(List.of(mockPassenger));

        when(bookingRepository.save(any())).thenReturn(saved);

        BookingResponse response =
                bookingService.bookTicket(10L, request, "test@gmail.com");

        assertNotNull(response);
        assertEquals("test@gmail.com", response.getUserEmail());
        assertEquals("TestAir", response.getAirlineName());
        assertEquals(10L, response.getFlightId());

        verify(bookingRepository, times(1)).save(any());
        verify(passengerRepository, times(1)).save(any());
    }

}
