package com.iprody.payment.service.app.dto;

import com.iprody.payment.service.app.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusDTO {
    PaymentStatus newStatus;
}
