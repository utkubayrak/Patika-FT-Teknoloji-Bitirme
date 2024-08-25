package com.utkubayrak.jetbusservice.client.reservation;

import com.utkubayrak.jetbusservice.client.reservation.dto.response.ReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "reservation-service", url = "localhost:8084/api/reservation")
public interface ReservationClient {

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getUserTicket(@PathVariable("userId") Long userId);

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<ReservationResponse>> getFlightTicket(@PathVariable("flightId") Long flightId);

    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllTicket();

    @GetMapping("/total-income")
    public ResponseEntity<BigDecimal> getTotalIncome();

}
