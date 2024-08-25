package com.utkubayrak.jetbusservice.dto.request;

import lombok.Data;

@Data
public class BuyTicketRequest {
    private Long flightId;
    private Integer ticketCount;
}
