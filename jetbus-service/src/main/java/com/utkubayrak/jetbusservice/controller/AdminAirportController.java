package com.utkubayrak.jetbusservice.controller;

import com.utkubayrak.jetbusservice.dto.request.AirportRequest;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.service.IAdminAirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/airport")
public class AdminAirportController {
    private final IAdminAirportService adminAirportService;

    @PostMapping("/create-airport")
    public ResponseEntity<Airport> createAirport(@RequestBody AirportRequest airportRequest){
        Airport airport = adminAirportService.createAirport(airportRequest);
        return ResponseEntity.ok(airport);
    }

    @PutMapping("/update-airport/{airportId}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long airportId, @RequestBody AirportRequest airportRequest){
        Airport airport = adminAirportService.updateAirport(airportId,airportRequest);
        return ResponseEntity.ok(airport);
    }
    @DeleteMapping("/delete-airport/{airportId}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long airportId){
        adminAirportService.deleteAirport(airportId);
        return ResponseEntity.ok("Havaalanı başarıyla silindi.");
    }


}
