package com.utkubayrak.reservationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    private Long userId;
    private Long flightId;
    private double price;
    private int ticketCount;
}
