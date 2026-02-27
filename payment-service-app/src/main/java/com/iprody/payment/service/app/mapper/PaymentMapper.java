package com.iprody.payment.service.app.mapper;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "guid", source = "guid")
    PaymentDTO toDTO(Payments payments);

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "guid", ignore = true)
    Payments toEntity(PaymentDTO paymentDTO);

    @Named("stringToStatus")
    default PaymentStatus stringToStatus(String status) {
        if (status == null) return null;
        return PaymentStatus.valueOf(status.toUpperCase());
    }

    @Named("statusToString")
    default String statusToString(PaymentStatus status) {
        return status != null ? status.name() : null;
    }
}
