package com.utkubayrak.jetbusservice.service;

import com.utkubayrak.jetbusservice.dto.request.BuyTicketRequest;
import com.utkubayrak.jetbusservice.dto.request.FlightSearchRequest;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.model.Flight;

import java.util.List;

public interface IFlightService {

        public boolean purchaseTicket(BuyTicketRequest buyTicketRequest, String jwt);

        public List<Flight> searchFlights(FlightSearchRequest flightSearchRequest);
        public List<City> getAllCities();
        public List<Airport> getAllAirport();
        public List<Flight> getAllFlight();
        public Flight getFlightById(Long flightId);


}
