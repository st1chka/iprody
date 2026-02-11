package com.iprody.payment.service.app.repository;

import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.enums.PaymentStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, UUID>,
        JpaSpecificationExecutor<Payments> {

    List<Payments> findByCurrencyAndStatus(String currency, PaymentStatus status);

    @Query("""
            SELECT p from Payments p
                where p.currency = :currency and p.amount > :minAmount
            """)
    List<Payments> filter(String currency, BigDecimal minAmount);
}
