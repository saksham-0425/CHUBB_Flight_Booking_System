package com.flightapp.service;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse bookTicket(Long flightId, BookingRequest request, String userEmail);

    BookingResponse getTicketByPnr(String pnr);

    List<BookingResponse> getBookingHistory(String email);

    void cancelBooking(String pnr);
}
