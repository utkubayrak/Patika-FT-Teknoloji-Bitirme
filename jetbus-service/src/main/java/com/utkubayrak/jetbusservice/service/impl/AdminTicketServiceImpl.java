package com.utkubayrak.jetbusservice.service.impl;

import com.utkubayrak.jetbusservice.client.reservation.ReservationClient;
import com.utkubayrak.jetbusservice.client.reservation.dto.response.ReservationResponse;
import com.utkubayrak.jetbusservice.service.IAdminTicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminTicketServiceImpl implements IAdminTicketService {
    private final ReservationClient reservationClient;
    private static final Logger logger = LoggerFactory.getLogger(AdminTicketServiceImpl.class);

    @Override
    public List<ReservationResponse> getUserTicket(Long userId) {
        logger.debug("Retrieving tickets for user ID: {}", userId);
        List<ReservationResponse> tickets = reservationClient.getUserTicket(userId).getBody();
        logger.info("Retrieved {} tickets for user ID: {}", tickets.size(), userId);
        return tickets;
    }

    @Override
    public List<ReservationResponse> getFlightTicket(Long flightId) {
        logger.debug("Retrieving tickets for flight ID: {}", flightId);
        List<ReservationResponse> tickets = reservationClient.getFlightTicket(flightId).getBody();
        logger.info("Retrieved {} tickets for flight ID: {}", tickets.size(), flightId);
        return tickets;
    }

    @Override
    public List<ReservationResponse> getAllTicket() {
        logger.debug("Retrieving all tickets");
        List<ReservationResponse> tickets = reservationClient.getAllTicket().getBody();
        logger.info("Retrieved {} tickets", tickets.size());
        return tickets;
    }

    @Override
    public BigDecimal getTotalIncome() {
        logger.debug("Retrieving total income");
        BigDecimal totalIncome = reservationClient.getTotalIncome().getBody();
        logger.info("Retrieved total income: {}", totalIncome);
        return totalIncome;
    }
}
