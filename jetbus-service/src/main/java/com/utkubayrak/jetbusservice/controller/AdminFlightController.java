package com.utkubayrak.jetbusservice.controller;

import com.utkubayrak.jetbusservice.dto.request.FlightRequest;
import com.utkubayrak.jetbusservice.model.Flight;
import com.utkubayrak.jetbusservice.service.impl.AdminFlightServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/flight")
public class AdminFlightController {
    private final AdminFlightServiceImpl adminFlightService;


    @PostMapping("/create-flight")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightRequest flightRequest){
        Flight flight = adminFlightService.createFlight(flightRequest);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/update-flight/{flightId}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long flightId, @RequestBody FlightRequest flightRequest){
        Flight flight = adminFlightService.updateFlight(flightId,flightRequest);
        return ResponseEntity.ok(flight);
    }
    @DeleteMapping("/delete-flight/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId){
        adminFlightService.deleteFlight(flightId);
        return ResponseEntity.ok("Uçuş başarıyla silindi.");
    }


}
