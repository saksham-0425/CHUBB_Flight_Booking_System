package com.flightapp.service.impl;
import java.math.BigDecimal;
import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.dto.response.PassengerResponse;
import com.flightapp.model.*;
import com.flightapp.repository.*;
import com.flightapp.service.BookingService;
import com.flightapp.util.PnrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final AppUserRepository userRepository;
    private final FlightRepository flightRepository;

    @Override
    public BookingResponse bookTicket(Long flightId, BookingRequest request, String userEmail) {

        AppUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() < request.getNumSeats()) {
            throw new RuntimeException("Not enough seats available");
        }

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setUser(user);
        booking.setStatus("CONFIRMED");
        booking.setBookingTime(LocalDateTime.now());
        booking.setPnr(PnrGenerator.generate());
        booking.setTotalPrice(
                flight.getPrice().multiply(BigDecimal.valueOf(request.getNumSeats()))
        );

        booking.setSeatsJson("NA");

        Booking savedBooking = bookingRepository.save(booking);

        request.getPassengers().forEach(p -> {
            Passenger passenger = new Passenger();
            passenger.setBooking(savedBooking);
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setGender(p.getGender());
            passenger.setSeatNumber("AUTO");
            passengerRepository.save(passenger);
        });

        flight.setAvailableSeats(flight.getAvailableSeats() - request.getNumSeats());
        flightRepository.save(flight);

        return toResponse(savedBooking);
    }

    @Override
    public BookingResponse getTicketByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("PNR not found"));
        return toResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingHistory(String email) {
        return bookingRepository.findByUser_Email(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void cancelBooking(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("PNR not found"));

        Flight flight = booking.getFlight();

        if (LocalDateTime.now().isAfter(flight.getDepartDatetime().minusHours(24))) {
            throw new RuntimeException("Cannot cancel within 24 hours of departure");
        }

        booking.setStatus("CANCELLED");

        int seatCount = booking.getPassengers().size();
        flight.setAvailableSeats(flight.getAvailableSeats() + seatCount);

        bookingRepository.save(booking);
        flightRepository.save(flight);
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .pnr(booking.getPnr())
                .userName(booking.getUser().getName())
                .userEmail(booking.getUser().getEmail())
                .flightId(booking.getFlight().getId())
                .flightNumber(booking.getFlight().getFlightNumber())
                .airlineName(booking.getFlight().getAirline().getName())
                .origin(booking.getFlight().getOrigin())
                .destination(booking.getFlight().getDestination())
                .departDatetime(booking.getFlight().getDepartDatetime())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .passengers(
                        booking.getPassengers().stream()
                                .map(p -> PassengerResponse.builder()
                                        .name(p.getName())
                                        .gender(p.getGender())
                                        .age(p.getAge())
                                        .mealPref(p.getMealPref())
                                        .seatNumber(p.getSeatNumber())
                                        .build()
                                ).toList()
                )
                .build();
    }
}
