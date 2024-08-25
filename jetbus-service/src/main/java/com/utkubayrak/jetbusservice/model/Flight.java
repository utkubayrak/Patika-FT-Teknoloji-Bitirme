package com.utkubayrak.jetbusservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;
    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    private Duration duration;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer availableSeats = 189;
}
