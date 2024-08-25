package com.utkubayrak.paymentservice.client.service;

import com.utkubayrak.paymentservice.client.ReservationClient;
import com.utkubayrak.paymentservice.client.dto.request.ReservationRequest;
import com.utkubayrak.paymentservice.client.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationClientService {
    private final ReservationClient reservationClient;

    public ReservationResponse addReservation(ReservationRequest reservationRequest){
        return reservationClient.addReservation(reservationRequest).getBody();
    }
}
