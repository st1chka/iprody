package service;

import com.iprody.payment.service.app.dto.PaymentDTO;
import com.iprody.payment.service.app.entity.Payments;
import com.iprody.payment.service.app.enums.PaymentStatus;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import com.iprody.payment.service.app.repository.PaymentRepository;
import com.iprody.payment.service.app.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    private Payments payment;
    private List<Payments> payments = new ArrayList<>();
    private PaymentDTO paymentDto;
    private UUID guid;

    @BeforeEach
    void setUp() {
        guid = UUID.randomUUID();

        payment = new Payments();
        payment.setGuid(guid);
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("USD");
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setCreatedAt(OffsetDateTime.now());
        payment.setUpdatedAt(OffsetDateTime.now());
        paymentDto = new PaymentDTO();
        paymentDto.setGuid(payment.getGuid());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setStatus(payment.getStatus().name());

        payments.add(payment);
    }

    @Test
    public void shouldReturnPaymentById(){
        //given
        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDto);

        //when
        PaymentDTO result = paymentService.getPaymentById(guid);

        //then 
        assertEquals(guid, result.getGuid());
        assertEquals("USD", result.getCurrency());
        assertEquals(PaymentStatus.APPROVED.toString(), result.getStatus());
        verify(paymentRepository).findById(guid);
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    public void shouldReturnPaymentAll(){
        //given
        when(paymentRepository.findAll()).thenReturn(payments);
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDto);

        //when
        List<PaymentDTO> result = paymentService.getAllPayments();

        //then
        assertNotNull(result);
        verify(paymentMapper).toDTO(payment);
    }
}
