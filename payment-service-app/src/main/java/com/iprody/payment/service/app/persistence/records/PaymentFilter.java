package com.iprody.payment.service.app.persistence.records;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentFilter(
        String currency,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        Instant createdAfter,
        Instant createdBefore) {

}
