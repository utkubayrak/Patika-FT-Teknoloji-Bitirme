package com.utkubayrak.paymentservice.client.dto.request;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long userId;
    private Long flightId;
    private double price;
    private int ticketCount;
}
