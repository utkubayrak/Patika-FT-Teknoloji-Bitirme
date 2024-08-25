package com.utkubayrak.reservationservice.controller;

import com.utkubayrak.reservationservice.dto.request.ReservationRequest;
import com.utkubayrak.reservationservice.model.Reservation;
import com.utkubayrak.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping("/add")
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest){
        return reservationService.createReservation(reservationRequest);
    }
    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @GetMapping("/flight/{flightId}")
    public List<Reservation> getReservationsByFlightId(@PathVariable Long flightId) {
        return reservationService.getReservationsByFlightId(flightId);
    }
    @GetMapping("/all")
    public List<Reservation> getAllReservation(){
        return reservationService.getAllReservation();
    }
    @GetMapping("/total-income")
    public BigDecimal getTotalIncome(){
        return reservationService.getTotalIncome();
    }
}
