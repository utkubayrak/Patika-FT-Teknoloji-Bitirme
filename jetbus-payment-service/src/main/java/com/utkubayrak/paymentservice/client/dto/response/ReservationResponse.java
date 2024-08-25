package com.utkubayrak.paymentservice.client.dto.response;

import lombok.Data;

@Data
public class ReservationResponse {
    private Long userId;
    private Long flightId;
}
