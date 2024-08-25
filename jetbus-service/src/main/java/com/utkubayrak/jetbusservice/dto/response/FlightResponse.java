package com.utkubayrak.jetbusservice.dto.response;

import com.utkubayrak.jetbusservice.model.Airport;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class FlightResponse {
    private String flightNumber;
    private Airport departureAirport;
    private Airport arrivalAirport;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Duration duration;
    private Double price;
    private Integer availableSeats;
}
