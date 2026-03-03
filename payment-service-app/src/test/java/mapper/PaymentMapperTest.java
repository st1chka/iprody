package mapper;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.enums.PaymentStatus;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PaymentMapperTest {
    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void shouldMapToEntity() {
        // given
        PaymentDTO payment = new PaymentDTO();
        payment.setAmount(new BigDecimal("123.45"));
        payment.setCurrency("USD");
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setStatus(PaymentStatus.APPROVED.name());
        payment.setCreatedAt(OffsetDateTime.now());
        payment.setUpdatedAt(OffsetDateTime.now());
        // when
        Payments dto = mapper.toEntity(payment);

        //then
        assertThat(dto).isNotNull();
        assertThat(dto.getAmount()).isEqualTo(payment.getAmount());
        assertThat(dto.getCurrency()).isEqualTo(payment.getCurrency());
        assertThat(dto.getInquiryRefId()).isEqualTo(payment.getInquiryRefId());
        assertThat(dto.getStatus().toString()).isEqualTo(payment.getStatus());
        assertThat(dto.getCreatedAt()).isEqualTo(payment.getCreatedAt());
        assertThat(dto.getUpdatedAt()).isEqualTo(payment.getUpdatedAt());
    }

    @Test
    void shouldMapToDTO() {
        // given
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        Payments entity = new Payments();
        entity.setGuid(id);
        entity.setInquiryRefId(UUID.randomUUID());
        entity.setAmount(new BigDecimal("999.99"));
        entity.setCurrency("EUR");
        entity.setTransactionRefId(UUID.randomUUID());
        entity.setStatus(PaymentStatus.PENDING);
        entity.setNote("Test payment");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        // when
        PaymentDTO dto = mapper.toDTO(entity);

        //then
        assertThat(dto).isNotNull();
        assertThat(dto.getGuid()).isEqualTo(entity.getGuid());
        assertThat(dto.getAmount()).isEqualTo(entity.getAmount());
        assertThat(dto.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(dto.getInquiryRefId()).isEqualTo(entity.getInquiryRefId());
        assertThat(dto.getStatus()).isEqualTo(entity.getStatus().toString());
        assertThat(dto.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(dto.getUpdatedAt()).isEqualTo(entity.getUpdatedAt());
    }
}