package com.iprody.payment.service.app.dto;

import com.iprody.payment.service.app.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private UUID guid;

    private UUID inquiryRefId;

    private BigDecimal amount;

    private String currency;

    private UUID transactionRefId;

    private String status;

    private String note;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public PaymentStatus getStatusEnum() {
        return PaymentStatus.valueOf(status.toUpperCase());
    }
}
