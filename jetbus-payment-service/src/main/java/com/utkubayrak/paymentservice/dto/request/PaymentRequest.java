package com.utkubayrak.paymentservice.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private Long flightId;
    private Double price;
    private int ticketCount;
}
