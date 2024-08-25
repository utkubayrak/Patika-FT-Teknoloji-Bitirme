package com.utkubayrak.jetbusservice.controller;

import com.utkubayrak.jetbusservice.dto.request.BuyTicketRequest;
import com.utkubayrak.jetbusservice.dto.request.FlightSearchRequest;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.model.Flight;
import com.utkubayrak.jetbusservice.service.IAdminCityService;
import com.utkubayrak.jetbusservice.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flight")
public class FlightController {
    private final IFlightService flightService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(
            @RequestBody BuyTicketRequest buyTicketRequest,
            @RequestHeader("Authorization") String jwt) {

        boolean success = flightService.purchaseTicket(buyTicketRequest, jwt);

        if (success) {
            return ResponseEntity.ok("Ticket purchased successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No seats available or payment failed.");
        }
    }
    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam(required = false) Long departureCityId,
                                      @RequestParam(required = false) Long arrivalCityId,
                                      @RequestParam LocalDate departureDate) {
        FlightSearchRequest request = new FlightSearchRequest(departureCityId, arrivalCityId, departureDate);
        return flightService.searchFlights(request);
    }
    @GetMapping("/all-city")
    public ResponseEntity<List<City>> getAllCity(){
        List<City> cities = flightService.getAllCities();
        return ResponseEntity.ok(cities);
    }
    @GetMapping("/all-airports")
    public ResponseEntity<List<Airport>> getAllAirports(){
        List<Airport> airports = flightService.getAllAirport();
        return ResponseEntity.ok(airports);
    }
    @GetMapping("/all-flights")
    public ResponseEntity<List<Flight>> getAllFlights(){
        List<Flight> flights = flightService.getAllFlight();
        return ResponseEntity.ok(flights);
    }
}
