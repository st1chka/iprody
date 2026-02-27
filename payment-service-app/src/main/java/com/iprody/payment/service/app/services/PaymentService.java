package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import com.iprody.payment.service.app.persistence.PaymentFilterFactory;
import com.iprody.payment.service.app.persistence.records.PaymentFilter;
import com.iprody.payment.service.app.repository.PaymentRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(UUID id) {
        return paymentRepository.findById(id).map(paymentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Payment not found by id: " + id));
    }

    public Page<PaymentDTO> searchPaged(PaymentFilter filter, Pageable
            pageable) {
        return paymentRepository.findAll(PaymentFilterFactory.fromFilter(filter), pageable)
                .map(paymentMapper::toDTO);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payments paymentDto = paymentRepository.save(paymentMapper.toEntity(paymentDTO));
        return paymentMapper.toDTO(paymentDto);
    }
}
