package com.flightapp.service.impl;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.request.PassengerRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.model.*;
import com.flightapp.repository.*;
import com.flightapp.util.PnrGenerator;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new AppUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Test User");

        flight = new Flight();
        flight.setId(10L);
        flight.setAvailableSeats(10);
        flight.setPrice(BigDecimal.valueOf(100));

    }

    @Test
    void testBookTicketSuccess() {

        AppUser user = new AppUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Test User");

        Flight flight = new Flight();
        flight.setId(10L);
        flight.setAvailableSeats(10);
        flight.setPrice(BigDecimal.valueOf(100));

        BookingRequest request = new BookingRequest();
        request.setNumSeats(2);

        PassengerRequest p = new PassengerRequest();
        p.setName("John");
        p.setAge(25);
        p.setGender("M");

        request.setPassengers(List.of(p));

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(flightRepository.findById(10L)).thenReturn(Optional.of(flight));

        Booking saved = new Booking();
        saved.setId(99L);
        saved.setUser(user);
        saved.setFlight(flight);
        saved.setPnr("PNR12345");

        when(bookingRepository.save(any())).thenReturn(saved);

        BookingResponse response = bookingService.bookTicket(10L, request, "test@gmail.com");

        assertNotNull(response);
        assertNotNull(response.getPnr());
        assertEquals("test@gmail.com", response.getUserEmail());

        verify(bookingRepository, times(1)).save(any());
    }

}
