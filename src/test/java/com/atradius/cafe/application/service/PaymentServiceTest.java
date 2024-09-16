package com.atradius.cafe.application.service;

import com.atradius.cafe.application.dto.UserPaymentDTO;
import com.atradius.cafe.domain.model.Payment;
import com.atradius.cafe.domain.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;


    @Test
    public void calculatePaidUser_ShouldReturnUserPaymentDTO_WhenPaymentsExist() {
        String user = "coach";

        Payment payment1 = new Payment(user, new BigDecimal("10.0"));
        Payment payment2 = new Payment(user, new BigDecimal("5.0"));
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentRepository.findByUser(user)).thenReturn(payments);

        UserPaymentDTO result = paymentService.calculatePaidUser(user);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(new BigDecimal("15.0"), result.getPaid());
    }

}
