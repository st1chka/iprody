package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Payment getPayment() {
        return new Payment(1, 3.5);
    }
}
