package com.utkubayrak.jetbusservice.service;

import com.utkubayrak.jetbusservice.client.reservation.dto.response.ReservationResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IAdminTicketService {
    public List<ReservationResponse> getUserTicket(Long userId);
    public List<ReservationResponse> getFlightTicket(Long flightId);
    public List<ReservationResponse> getAllTicket();
    public BigDecimal getTotalIncome();
}
