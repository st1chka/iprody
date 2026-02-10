package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.persistence.PaymentFilterFactory;
import com.iprody.payment.service.app.persistence.records.PaymentFilter;
import com.iprody.payment.service.app.repository.PaymentRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payments> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payments getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found by id: " + id));
    }

    public Page<Payments> searchPaged(PaymentFilter filter, Pageable
            pageable) {
        return paymentRepository.findAll(PaymentFilterFactory.fromFilter(filter), pageable);
    }
}
