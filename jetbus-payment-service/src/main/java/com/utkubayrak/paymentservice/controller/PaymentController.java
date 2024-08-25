package com.utkubayrak.paymentservice.controller;

import com.utkubayrak.paymentservice.dto.request.PaymentRequest;
import com.utkubayrak.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/buy-ticket")
    public boolean processPayment(@RequestBody PaymentRequest paymentRequest){
        return paymentService.processPayment(paymentRequest);
    }
}
