package com.utkubayrak.jetbusservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class FlightSearchRequest {
    private Long departureCityId;
    private Long arrivalCityId;
    private LocalDate departureDate;

}
