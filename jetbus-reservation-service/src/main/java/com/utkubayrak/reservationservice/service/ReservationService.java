package com.utkubayrak.reservationservice.service;

import com.utkubayrak.reservationservice.dto.request.ReservationRequest;
import com.utkubayrak.reservationservice.model.Reservation;
import com.utkubayrak.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public Reservation createReservation(ReservationRequest reservationRequest) {
        logger.info("Creating reservation for user ID: {}, flight ID: {}",
                reservationRequest.getUserId(), reservationRequest.getFlightId());

        Reservation reservation = new Reservation();
        reservation.setUserId(reservationRequest.getUserId());
        reservation.setFlightId(reservationRequest.getFlightId());
        reservation.setPrice(reservationRequest.getPrice());
        reservation.setTicketCount(reservationRequest.getTicketCount());

        Reservation savedReservation = reservationRepository.save(reservation);
        logger.info("Reservation created successfully with ID: {}", savedReservation.getId());
        return savedReservation;
    }

    public List<Reservation> getAllReservation() {
        logger.info("Fetching all reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        logger.info("Total {} reservations found", reservations.size());
        return reservations;
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        logger.info("Fetching reservations for user ID: {}", userId);
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        logger.info("Total {} reservations found for user ID: {}", reservations.size(), userId);
        return reservations;
    }

    public List<Reservation> getReservationsByFlightId(Long flightId) {
        logger.info("Fetching reservations for flight ID: {}", flightId);
        List<Reservation> reservations = reservationRepository.findByFlightId(flightId);
        logger.info("Total {} reservations found for flight ID: {}", reservations.size(), flightId);
        return reservations;
    }

    public BigDecimal getTotalIncome() {
        logger.info("Calculating total income");

        TypedAggregation<Reservation> aggregation = Aggregation.newAggregation(
                Reservation.class,
                Aggregation.group().sum("price").as("totalIncome")
        );

        AggregationResults<IncomeResult> results = mongoTemplate.aggregate(aggregation, IncomeResult.class);
        IncomeResult result = results.getUniqueMappedResult();

        BigDecimal totalIncome = result != null ? result.getTotalIncome() : BigDecimal.ZERO;
        logger.info("Total income calculated: {}", totalIncome);

        return totalIncome;
    }

    private static class IncomeResult {
        private BigDecimal totalIncome;

        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(BigDecimal totalIncome) {
            this.totalIncome = totalIncome;
        }
    }
}
