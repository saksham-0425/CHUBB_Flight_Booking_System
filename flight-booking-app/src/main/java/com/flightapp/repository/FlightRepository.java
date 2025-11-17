package com.flightapp.repository;

import com.flightapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f " +
           "WHERE f.origin = :origin " +
           "AND f.destination = :destination " +
           "AND f.departDatetime BETWEEN :startDate AND :endDate")
    List<Flight> searchFlights(String origin,
                               String destination,
                               LocalDateTime startDate,
                               LocalDateTime endDate);
}
