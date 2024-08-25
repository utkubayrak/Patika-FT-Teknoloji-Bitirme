package com.utkubayrak.jetbusservice.client.payment.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private Long flightId;
    private double price;
    private int ticketCount;
}
