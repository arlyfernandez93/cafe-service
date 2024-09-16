package com.atradius.cafe.application.service;

import com.atradius.cafe.application.dto.UserDebtDTO;
import com.atradius.cafe.domain.model.Order;
import com.atradius.cafe.domain.model.Payment;
import com.atradius.cafe.domain.model.Price;
import com.atradius.cafe.domain.model.Product;
import com.atradius.cafe.domain.repository.OrderRepository;
import com.atradius.cafe.domain.repository.PaymentRepository;
import com.atradius.cafe.domain.repository.ProductRepository;
import com.atradius.cafe.infrastructure.exception.OverpaymentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DebtServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DebtService debtService;


    @Test
    public void calculateDebtPerCustomer_ShouldReturnDebt_WhenPaymentsLessThanOrderCost() {
        String user = "coach";

        Price price1 = new Price(Map.of("small", new BigDecimal("3.0")));
        Price price2 = new Price(Map.of("large", new BigDecimal("5.0")));
        List<Product> products = Arrays.asList(
                new Product("espresso", price1),
                new Product("latte", price2)
        );

        Order order1 = new Order("coach","espresso", "small");
        Order order2 = new Order("coach","latte", "large");
        List<Order> orders = Arrays.asList(order1, order2);

        Payment payment = new Payment("coach",new BigDecimal("3.0"));
        List<Payment> payments = Arrays.asList(payment);

        when(orderRepository.findByUser(user)).thenReturn(orders);
        when(paymentRepository.findByUser(user)).thenReturn(payments);
        when(productRepository.findAll()).thenReturn(products);

        UserDebtDTO result = debtService.calculateDebtPerCustomer(user);

        assertNotNull(result);
        assertEquals(new BigDecimal("5.0"), result.getOutstandingAmount());
    }

    @Test
    public void calculateDebtPerCustomer_ShouldThrowOverpaymentException_WhenPaymentsGreaterThanOrderCost() {
        String user = "coach";

        Price espressoPrice = new Price(Map.of("small", new BigDecimal("3.0")));
        List<Product> products = Arrays.asList(
                new Product("espresso", espressoPrice)
        );

        Order order1 = new Order("coach", "espresso", "small");
        List<Order> orders = Arrays.asList(order1);

        Payment payment = new Payment("coach", new BigDecimal("5.0"));
        List<Payment> payments = Arrays.asList(payment);

        when(orderRepository.findByUser(user)).thenReturn(orders);
        when(paymentRepository.findByUser(user)).thenReturn(payments);
        when(productRepository.findAll()).thenReturn(products);

        OverpaymentException thrown = assertThrows(OverpaymentException.class, () -> {
            debtService.calculateDebtPerCustomer(user);
        });

        assertEquals("User coach overpaid a total of 2.0", thrown.getMessage());
    }

}
