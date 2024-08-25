package com.utkubayrak.jetbusservice.service;

import com.utkubayrak.jetbusservice.client.reservation.dto.response.ReservationResponse;
import com.utkubayrak.jetbusservice.dto.request.FlightRequest;
import com.utkubayrak.jetbusservice.dto.response.FlightResponse;
import com.utkubayrak.jetbusservice.model.Flight;

import java.math.BigDecimal;
import java.util.List;

public interface IAdminFlightService {
    public Flight createFlight(FlightRequest flightRequest);
    public Flight updateFlight(Long flightId,FlightRequest flightRequest);
    public void deleteFlight(Long flightId);


}
