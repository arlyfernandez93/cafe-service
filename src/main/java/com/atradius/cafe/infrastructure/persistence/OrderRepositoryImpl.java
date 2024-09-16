package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Order;
import com.atradius.cafe.domain.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Value("${datasource.orders}")
    private String orderFilePath;

    private final ObjectMapper objectMapper;

    public OrderRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Order> findByUser(String user) {
        List<Order> allOrders = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(orderFilePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: orders.json");
            }
            allOrders = objectMapper.readValue(inputStream, new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allOrders.stream()
                .filter(order -> order.getUser().equalsIgnoreCase(user))
                .toList();
    }
}
