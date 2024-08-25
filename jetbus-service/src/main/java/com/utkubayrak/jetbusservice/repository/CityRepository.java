package com.utkubayrak.jetbusservice.repository;

import com.utkubayrak.jetbusservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByCityNameAndCountryName(String cityName, String countryName);
}
