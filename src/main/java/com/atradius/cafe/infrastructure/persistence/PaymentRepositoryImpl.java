package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Payment;
import com.atradius.cafe.domain.repository.PaymentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final ObjectMapper objectMapper;

    @Value("${datasource.payments}")
    private String paymentFilePath;

    public PaymentRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Payment> findByUser(String user) {
        List<Payment> allPayments = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(paymentFilePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: payments.json");
            }
            allPayments = objectMapper.readValue(inputStream, new TypeReference<List<Payment>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPayments.stream()
                .filter(payment -> payment.getUser().equalsIgnoreCase(user))
                .toList();
    }
}
