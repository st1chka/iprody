package com.iprody.payment.service.app.persistence;

import com.iprody.payment.service.app.entity.Payments;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.jpa.domain.Specification;

public final class PaymentSpecifications {

    public static Specification<Payments> hasCurrency(String currency) {
        return (root, query, cb) -> cb.equal(root.get("currency"), currency);
    }

    public static Specification<Payments> amountBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> cb.between(root.get("amount"), min, max);
    }

    public static Specification<Payments> createdBetween(Instant after, Instant before) {
        return (root, query, cb) -> cb.between(root.get("createdAt"), after, before);
    }
}
