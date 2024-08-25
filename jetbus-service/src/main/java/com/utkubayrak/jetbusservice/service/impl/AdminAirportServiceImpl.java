package com.utkubayrak.jetbusservice.service.impl;

import com.utkubayrak.jetbusservice.dto.request.AirportRequest;
import com.utkubayrak.jetbusservice.exception.ExceptionMessages;
import com.utkubayrak.jetbusservice.exception.JetbusException;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.repository.AirportRepository;
import com.utkubayrak.jetbusservice.repository.CityRepository;
import com.utkubayrak.jetbusservice.service.IAdminAirportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminAirportServiceImpl implements IAdminAirportService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminAirportServiceImpl.class);

    @Override
    public Airport createAirport(AirportRequest airportRequest) {
        logger.debug("Creating airport with request: {}", airportRequest);
        Optional<City> cityOpt = cityRepository.findById(airportRequest.getCityId());
        if (cityOpt.isEmpty()) {
            logger.error("City with ID {} not found", airportRequest.getCityId());
            throw new JetbusException(ExceptionMessages.CITY_NOT_FOUND);
        }
        City city = cityOpt.get();

        Airport airport = new Airport();
        airport.setAirportName(airportRequest.getAirportName());
        airport.setCity(city);
        airportRepository.save(airport);

        logger.info("Created airport: {}", airport);
        return airport;
    }

    @Override
    public Airport updateAirport(Long airportId, AirportRequest airportRequest) {
        logger.debug("Updating airport with ID {} and request: {}", airportId, airportRequest);
        Airport airport = getAirportById(airportId);
        if (airportRequest.getCityId() != null) {
            Optional<City> cityOpt = cityRepository.findById(airportRequest.getCityId());
            if (cityOpt.isEmpty()) {
                logger.error("City with ID {} not found", airportRequest.getCityId());
                throw new JetbusException(ExceptionMessages.CITY_NOT_FOUND);
            }
            City city = cityOpt.get();
            airport.setCity(city);
        }
        if (airportRequest.getAirportName() != null && !airportRequest.getAirportName().isEmpty()) {
            airport.setAirportName(airportRequest.getAirportName());
        }
        airportRepository.save(airport);

        logger.info("Updated airport: {}", airport);
        return airport;
    }

    @Override
    public void deleteAirport(Long airportId) {
        logger.debug("Deleting airport with ID {}", airportId);
        getAirportById(airportId);
        airportRepository.deleteById(airportId);
        logger.info("Deleted airport with ID {}", airportId);
    }



    @Override
    public Airport getAirportById(Long airportId) {
        logger.debug("Retrieving airport with ID {}", airportId);
        Optional<Airport> airportOpt = airportRepository.findById(airportId);
        if (airportOpt.isEmpty()) {
            logger.error("Airport with ID {} not found", airportId);
            throw new JetbusException(ExceptionMessages.AIRPORT_NOT_FOUND);
        }
        Airport airport = airportOpt.get();
        logger.info("Retrieved airport: {}", airport);
        return airport;
    }
}
