package com.utkubayrak.jetbusservice.client.payment;

import com.utkubayrak.jetbusservice.client.payment.dto.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "localhost:8083/api/payment")
public interface PaymentClient {

    @PostMapping("buy-ticket")
    public boolean processPayment(@RequestBody PaymentRequest paymentRequest);
}
