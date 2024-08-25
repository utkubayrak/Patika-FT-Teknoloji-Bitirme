package com.utkubayrak.jetbusservice.service;

import com.utkubayrak.jetbusservice.dto.request.AirportRequest;
import com.utkubayrak.jetbusservice.model.Airport;

import java.util.List;

public interface IAdminAirportService {

    public Airport createAirport(AirportRequest airportRequest);
    public Airport updateAirport(Long airportId,AirportRequest airportRequest);
    public void deleteAirport(Long airportId);
    public Airport getAirportById(Long airportId);

}
