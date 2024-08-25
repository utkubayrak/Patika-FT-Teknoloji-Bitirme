package com.utkubayrak.jetbusservice.repository;

import com.utkubayrak.jetbusservice.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport,Long> {
}
