package com.iprody.payment.service.app.repository;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, UUID>,
        JpaSpecificationExecutor<Payments> {

    @Query("""
            SELECT p from Payments p
                where p.currency = :currency and p.amount > :minAmount
            """)
    List<Payments> filter(String currency, BigDecimal minAmount);

    @Query("""
            update Payments p set p.status = :newStatus where p.guid = :id
            """)
    PaymentDTO updateStatus(UUID id, PaymentStatus newStatus);
}
