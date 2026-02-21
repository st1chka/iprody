package com.iprody.payment.service.app.controllers;

import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.persistence.records.PaymentFilter;
import com.iprody.payment.service.app.services.PaymentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    public List<Payments> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payments getAllPayments(@PathVariable UUID id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/search")
    public Page<Payments> searchPayments(@ModelAttribute PaymentFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "guid") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = switch (direction) {
            case "desc" -> Sort.by(sortBy).descending();
            case "asc" -> Sort.by(sortBy).ascending();
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };

        Pageable pageable = PageRequest.of(page, size, sort);
        return paymentService.searchPaged(filter, pageable);
    }
}
