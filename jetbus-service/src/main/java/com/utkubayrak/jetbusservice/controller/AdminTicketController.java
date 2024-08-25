package com.utkubayrak.jetbusservice.controller;

import com.utkubayrak.jetbusservice.client.reservation.dto.response.ReservationResponse;
import com.utkubayrak.jetbusservice.service.IAdminTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/ticket")
public class AdminTicketController {

    private final IAdminTicketService adminTicketService;

    @GetMapping("/all-ticket")
    public ResponseEntity<List<ReservationResponse>> getAllTicket(){
        return ResponseEntity.ok(adminTicketService.getAllTicket());
    }
    @GetMapping("/user-ticket/{userId}")
    public ResponseEntity<List<ReservationResponse>> getUserTicket(@PathVariable Long userId){
        return ResponseEntity.ok(adminTicketService.getUserTicket(userId));
    }
    @GetMapping("/flight-ticket/{flightId}")
    public ResponseEntity<List<ReservationResponse>> getFlightTicket(@PathVariable Long flightId){
        return ResponseEntity.ok(adminTicketService.getFlightTicket(flightId));
    }
    @GetMapping("/total-income")
    public ResponseEntity<BigDecimal> getTotalIncome(){
        return ResponseEntity.ok(adminTicketService.getTotalIncome());
    }
}
