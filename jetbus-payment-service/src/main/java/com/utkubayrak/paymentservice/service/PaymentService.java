package com.utkubayrak.paymentservice.service;

import com.utkubayrak.paymentservice.client.ReservationClient;
import com.utkubayrak.paymentservice.client.dto.request.ReservationRequest;
import com.utkubayrak.paymentservice.dto.request.PaymentRequest;
import com.utkubayrak.paymentservice.controller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final ReservationClient reservationClient;
    private final NotificationController notificationController;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public boolean processPayment(PaymentRequest paymentRequest) {
        logger.info("Starting payment processing for user ID: {}, flight ID: {}",
                paymentRequest.getUserId(), paymentRequest.getFlightId());

        boolean paymentSuccessful = true;

        try {
            logger.debug("PaymentRequest details: {}", paymentRequest);
            if (paymentSuccessful) {
                logger.info("Payment successful for user ID: {}, proceeding to reservation.",
                        paymentRequest.getUserId());
                ReservationRequest reservationRequest = new ReservationRequest();
                reservationRequest.setFlightId(paymentRequest.getFlightId());
                reservationRequest.setUserId(paymentRequest.getUserId());
                reservationRequest.setPrice(paymentRequest.getPrice());
                reservationRequest.setTicketCount(paymentRequest.getTicketCount());
                logger.debug("ReservationRequest details: {}", reservationRequest);
                reservationClient.addReservation(reservationRequest);
                logger.info("Reservation completed for user ID: {}, flight ID: {}",
                        paymentRequest.getUserId(), paymentRequest.getFlightId());
                notificationController.sendNotification(
                        "Payment successful for user ID: " + paymentRequest.getUserId() +
                                " for flight ID: " + paymentRequest.getFlightId());
            }
        } catch (Exception e) {
            logger.error("Error occurred during payment processing for user ID: {}: {}",
                    paymentRequest.getUserId(), e.getMessage(), e);
            paymentSuccessful = false;
        }
        logger.info("Payment process finished for user ID: {}, success: {}",
                paymentRequest.getUserId(), paymentSuccessful);
        return paymentSuccessful;
    }
}
