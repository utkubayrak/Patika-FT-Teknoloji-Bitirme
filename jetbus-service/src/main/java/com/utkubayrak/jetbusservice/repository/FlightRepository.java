package com.utkubayrak.jetbusservice.repository;

import com.utkubayrak.jetbusservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    @Query("SELECT f FROM Flight f WHERE " +
            "(:departureCityId IS NULL OR f.departureAirport.city.id = :departureCityId) AND " +
            "(:arrivalCityId IS NULL OR f.arrivalAirport.city.id = :arrivalCityId) AND " +
            "(DATE(f.departureTime) = :departureDate)")
    List<Flight> searchFlights(@Param("departureCityId") Long departureCityId,
                               @Param("arrivalCityId") Long arrivalCityId,
                               @Param("departureDate") LocalDate departureDate);

}
