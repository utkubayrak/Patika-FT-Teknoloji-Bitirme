package com.utkubayrak.jetbusservice.client.reservation.dto.response;

import lombok.Data;

@Data
public class ReservationResponse {
    private Long userId;
    private Long flightId;
    private double price;
    private int ticketCount;
}
