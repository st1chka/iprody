package com.iprody.payment.service.app.controllers;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.dto.UpdatePaymentStatusDTO;
import com.iprody.payment.service.app.persistence.records.PaymentFilter;
import com.iprody.payment.service.app.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentDTO getPayment(@PathVariable UUID id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/create")
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @PatchMapping("/{id}/status")
    public PaymentDTO updatePaymentStatus(@PathVariable UUID id, UpdatePaymentStatusDTO dto) {
        return paymentService.updatePaymentStatus(id, dto.getNewStatus());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable UUID id) {
        paymentService.deleteById(id);
    }

    @GetMapping("/search")
    public Page<PaymentDTO> searchPayments(@ModelAttribute PaymentFilter filter,
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
