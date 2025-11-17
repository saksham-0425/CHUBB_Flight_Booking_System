package com.flightapp.controller;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.service.BookingService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking/{flightId}")
    public BookingResponse bookTicket(
            @PathVariable Long flightId,
            @RequestBody @Valid BookingRequest request,
            HttpSession session) {

        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail == null) {
            throw new RuntimeException("User must be logged in to book");
        }

        return bookingService.bookTicket(flightId, request, userEmail);
    }

    @GetMapping("/ticket/{pnr}")
    public BookingResponse getTicket(@PathVariable String pnr) {
        return bookingService.getTicketByPnr(pnr);
    }

    @DeleteMapping("/booking/cancel/{pnr}")
    public String cancelBooking(@PathVariable String pnr) {
        bookingService.cancelBooking(pnr);
        return "Booking cancelled successfully";
    }
}
