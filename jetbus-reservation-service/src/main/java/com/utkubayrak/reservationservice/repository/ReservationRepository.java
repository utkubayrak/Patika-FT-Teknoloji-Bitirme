package com.utkubayrak.reservationservice.repository;


import com.utkubayrak.reservationservice.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByFlightId(Long flightId);

}
