package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Order;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryImplTest {

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        setPrivateField(orderRepository, "orderFilePath", "static/orders.json");
    }
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }


    @Test
    public void findByUser_ShouldReturnOrders_ForValidUser() throws IOException {
        String user = "coach";
        List<Order> mockOrders = new ArrayList<>();
        Order order = new Order("coach", "espresso", "small");
        mockOrders.add(order);

        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(mockOrders);

        List<Order> result = orderRepository.findByUser(user);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

}
