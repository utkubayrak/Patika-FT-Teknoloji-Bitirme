package com.utkubayrak.jetbusservice.dto.request;

import com.utkubayrak.jetbusservice.model.City;
import lombok.Data;

@Data
public class AirportRequest {
    private String airportName;
    private Long cityId;
}
