package com.utkubayrak.paymentservice.client;

import com.utkubayrak.paymentservice.client.dto.request.ReservationRequest;
import com.utkubayrak.paymentservice.client.dto.response.ReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "reservation-service", url = "localhost:8084/api/reservation")
public interface ReservationClient {

    @PostMapping("/add")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest);
}
