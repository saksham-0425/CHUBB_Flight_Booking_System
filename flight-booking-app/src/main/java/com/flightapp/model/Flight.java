package com.flightapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    private LocalDateTime departDatetime;

    @NotNull
    private LocalDateTime arriveDatetime;

    @NotNull
    private Integer durationMin;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer totalSeats;

    @NotNull
    private Integer availableSeats;

    @Version
    private Long version; 
}
