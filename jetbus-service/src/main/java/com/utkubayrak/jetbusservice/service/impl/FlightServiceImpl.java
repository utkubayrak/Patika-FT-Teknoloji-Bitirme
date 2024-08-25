package com.utkubayrak.jetbusservice.service.impl;

import com.utkubayrak.jetbusservice.client.payment.dto.request.PaymentRequest;
import com.utkubayrak.jetbusservice.client.payment.PaymentClient;
import com.utkubayrak.jetbusservice.client.user.UserClient;
import com.utkubayrak.jetbusservice.client.user.dto.response.RoleEnum;
import com.utkubayrak.jetbusservice.client.user.dto.response.UserResponse;
import com.utkubayrak.jetbusservice.dto.request.BuyTicketRequest;
import com.utkubayrak.jetbusservice.dto.request.FlightSearchRequest;
import com.utkubayrak.jetbusservice.exception.ExceptionMessages;
import com.utkubayrak.jetbusservice.exception.JetbusException;
import com.utkubayrak.jetbusservice.model.Airport;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.model.Flight;
import com.utkubayrak.jetbusservice.repository.AirportRepository;
import com.utkubayrak.jetbusservice.repository.CityRepository;
import com.utkubayrak.jetbusservice.repository.FlightRepository;
import com.utkubayrak.jetbusservice.service.IFlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements IFlightService {
    private final FlightRepository flightRepository;
    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final PaymentClient paymentClient;
    private final UserClient userClient;
    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Transactional
    @Override
    public boolean purchaseTicket(BuyTicketRequest buyTicketRequest, String jwt) {
        logger.debug("Attempting to purchase ticket with request: {}", buyTicketRequest);

        Flight flight = flightRepository.findById(buyTicketRequest.getFlightId())
                .orElseThrow(() -> {
                    logger.error("Flight not found with ID: {}", buyTicketRequest.getFlightId());
                    return new JetbusException(ExceptionMessages.FLIGHT_NOT_FOUND);
                });

        if (flight.getAvailableSeats() <= 0) {
            logger.warn("No available seats for flight ID: {}", buyTicketRequest.getFlightId());
            return false;
        }

        UserResponse userResponse = userClient.getUserByJwt(jwt).getBody();
        int maxTickets = 0;
        if (RoleEnum.INDIVIDUAL == userResponse.getRole()) {
            maxTickets = 5;
        } else if (RoleEnum.CORPORATE == userResponse.getRole()) {
            maxTickets = 40;
        }

        // Bilet limitini kontrol et
        if (buyTicketRequest.getTicketCount() > maxTickets) {
            logger.error("Ticket limit exceeded for user ID: {} with requested ticket count: {}",
                    userResponse.getId(), buyTicketRequest.getTicketCount());
            throw new JetbusException(ExceptionMessages.TICKET_LIMIT_EXCEEDED);
        }

        double totalPrice = buyTicketRequest.getTicketCount() * flight.getPrice();
        logger.info("Processing payment for flight ID: {} with total price: {} and ticket count: {}",
                flight.getId(), totalPrice, buyTicketRequest.getTicketCount());

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setFlightId(flight.getId());
        paymentRequest.setUserId(userResponse.getId());
        paymentRequest.setPrice(totalPrice);
        paymentRequest.setTicketCount(buyTicketRequest.getTicketCount());

        boolean paymentSuccessful = paymentClient.processPayment(paymentRequest);

        if (paymentSuccessful) {
            flight.setAvailableSeats(flight.getAvailableSeats() - buyTicketRequest.getTicketCount());
            flightRepository.save(flight);
            logger.info("Ticket purchase successful for flight ID: {}. Remaining seats: {}",
                    flight.getId(), flight.getAvailableSeats());
            return true;
        } else {
            logger.error("Payment failed for flight ID: {}", flight.getId());
            return false;
        }
    }

    @Override
    public List<Flight> searchFlights(FlightSearchRequest flightSearchRequest) {
        logger.debug("Searching for flights with request: {}", flightSearchRequest);
        List<Flight> flights = flightRepository.searchFlights(
                flightSearchRequest.getDepartureCityId(),
                flightSearchRequest.getArrivalCityId(),
                flightSearchRequest.getDepartureDate()
        );
        logger.info("Found {} flights matching the search criteria", flights.size());
        return flights;
    }
    @Override
    public List<City> getAllCities() {
        logger.info("Fetching all cities");
        return cityRepository.findAll();
    }
    @Override
    public List<Airport> getAllAirport() {
        logger.debug("Retrieving all airports");
        List<Airport> airports = airportRepository.findAll();
        logger.info("Retrieved {} airports", airports.size());
        return airports;
    }
    @Override
    public List<Flight> getAllFlight() {
        logger.debug("Retrieving all flights");
        List<Flight> flights = flightRepository.findAll();
        logger.info("Retrieved {} flights", flights.size());
        return flights;
    }

    @Override
    public Flight getFlightById(Long flightId) {
        logger.debug("Retrieving flight with ID {}", flightId);
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> {
                    logger.error("Flight with ID {} not found", flightId);
                    return new JetbusException(ExceptionMessages.FLIGHT_NOT_FOUND);
                });
        logger.info("Retrieved flight: {}", flight);
        return flight;
    }
}
