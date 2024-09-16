package com.atradius.cafe.application.service;

import com.atradius.cafe.application.dto.UserDebtDTO;
import com.atradius.cafe.application.mapper.UserDebtDTOMapper;
import com.atradius.cafe.domain.model.Order;
import com.atradius.cafe.domain.model.Payment;
import com.atradius.cafe.domain.model.Price;
import com.atradius.cafe.domain.model.Product;
import com.atradius.cafe.domain.repository.OrderRepository;
import com.atradius.cafe.domain.repository.PaymentRepository;
import com.atradius.cafe.domain.repository.ProductRepository;
import com.atradius.cafe.infrastructure.exception.OverpaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.atradius.cafe.infrastructure.util.CafeUtil.isNegative;

 @Slf4j
@Service
public class DebtService {
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public DebtService(ProductRepository productRepository, PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public UserDebtDTO calculateDebtPerCustomer(String user) {
        List<Order> userOrders = orderRepository.findByUser(user);
        List<Payment> userPayments = paymentRepository.findByUser(user);

        Map<String, Price> productPriceMap = getPriceByProducts();

        BigDecimal totalOrderCost = calculateTotalDebt(userOrders, productPriceMap);

        BigDecimal totalPayments = calculateTotalPaid(userPayments);

        BigDecimal OutstandingAmount = totalOrderCost.subtract(totalPayments);

        if (isNegative(OutstandingAmount)) {
            throw new OverpaymentException("User "+user+" overpaid a total of "+OutstandingAmount.abs());
        }

        return UserDebtDTOMapper.INSTANCE.toUserDebtDTO(user, OutstandingAmount);
    }

     private BigDecimal calculateTotalPaid(List<Payment> userPayments) {
         return userPayments.stream()
                 .map(Payment::getAmount)
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
     }

     private BigDecimal calculateTotalDebt(List<Order> userOrders, Map<String, Price> productPriceMap) {

         return userOrders.stream()
                 .map(order -> {
                     String size = order.getSize();
                     Price priceObject = productPriceMap.get(order.getDrink());
                     BigDecimal price = priceObject != null ? priceObject.getPrices().get(size) : BigDecimal.ZERO;
                     return price != null ? price : BigDecimal.ZERO;
                 })
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
     }

     private Map<String, Price> getPriceByProducts() {
         List<Product> products = productRepository.findAll();

         return products.stream()
                 .collect(Collectors.toMap(Product::getDrinkName, Product::getPrices));
     }
 }