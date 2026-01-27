package com.iprody.payment.service.app.controllers;

import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public Payment getPayment() {
        return paymentService.getPayment();
    }
}
