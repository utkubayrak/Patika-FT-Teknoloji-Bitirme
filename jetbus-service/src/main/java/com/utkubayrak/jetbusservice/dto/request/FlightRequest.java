package com.utkubayrak.jetbusservice.dto.request;

import com.utkubayrak.jetbusservice.model.Airport;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class FlightRequest {
    private String flightNumber;
    private Long departureAirportId;
    private Long arrivalAirportId;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private Integer availableSeats;
}
