package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Payment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class PaymentRepositoryImplTest {

    @Mock
    private ObjectMapper objectMapper;

     @InjectMocks
    private PaymentRepositoryImpl paymentRepository;

    @BeforeEach
    public void setUp() throws Exception {
        setPrivateField(paymentRepository, "paymentFilePath", "static/payments.json");
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void findByUser_ShouldReturnPayments_ForValidUser() throws IOException {
        String user = "coach";
        List<Payment> mockPayments = new ArrayList<>();
        Payment payment = new Payment(user, new BigDecimal("10.0"));
        mockPayments.add(payment);

        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(mockPayments);

        List<Payment> result = paymentRepository.findByUser(user);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));
    }

}
