package com.utkubayrak.jetbusservice.service.impl;

import com.utkubayrak.jetbusservice.dto.request.FlightRequest;
import com.utkubayrak.jetbusservice.exception.ExceptionMessages;
import com.utkubayrak.jetbusservice.exception.JetbusException;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.model.Flight;
import com.utkubayrak.jetbusservice.repository.FlightRepository;
import com.utkubayrak.jetbusservice.service.IAdminAirportService;
import com.utkubayrak.jetbusservice.service.IAdminFlightService;
import com.utkubayrak.jetbusservice.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminFlightServiceImpl implements IAdminFlightService {
    private final FlightRepository flightRepository;
    private final IAdminAirportService adminAirportService;
    private final IFlightService flightService;
    private static final Logger logger = LoggerFactory.getLogger(AdminFlightServiceImpl.class);

    @Override
    public Flight createFlight(FlightRequest flightRequest) {
        logger.debug("Creating flight with request: {}", flightRequest);
        Flight flight = new Flight();
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setPrice(flightRequest.getPrice());
        flight.setAvailableSeats(flightRequest.getAvailableSeats());

        Airport arrivalAirport = adminAirportService.getAirportById(flightRequest.getArrivalAirportId());
        Airport departureAirport = adminAirportService.getAirportById(flightRequest.getDepartureAirportId());
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);

        Duration flightDuration = calculateDuration(flight.getDepartureTime(), flight.getArrivalTime());
        flight.setDuration(flightDuration);

        flightRepository.save(flight);
        logger.info("Created flight: {}", flight);
        return flight;
    }

    private Duration calculateDuration(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (departureTime != null && arrivalTime != null) {
            return Duration.between(departureTime, arrivalTime);
        } else {
            logger.error("Departure or arrival time is not set");
            throw new JetbusException(ExceptionMessages.DEPARTURE_ARRIVAL_TIME_NOT_SET);
        }
    }

    @Override
    public Flight updateFlight(Long flightId, FlightRequest flightRequest) {
        logger.debug("Updating flight with ID {} and request: {}", flightId, flightRequest);
        Flight flight = flightService.getFlightById(flightId);
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setPrice(flightRequest.getPrice());
        flight.setAvailableSeats(flightRequest.getAvailableSeats());

        Airport arrivalAirport = adminAirportService.getAirportById(flightRequest.getArrivalAirportId());
        Airport departureAirport = adminAirportService.getAirportById(flightRequest.getDepartureAirportId());
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);

        Duration flightDuration = calculateDuration(flight.getDepartureTime(), flight.getArrivalTime());
        flight.setDuration(flightDuration);

        flightRepository.save(flight);
        logger.info("Updated flight: {}", flight);
        return flight;
    }

    @Override
    public void deleteFlight(Long flightId) {
        logger.debug("Deleting flight with ID {}", flightId);
        Optional<Flight> flight = flightRepository.findById(flightId);
        flightService.getFlightById(flightId);
        flightRepository.deleteById(flightId);
        logger.info("Deleted flight with ID {}", flightId);
    }


}
