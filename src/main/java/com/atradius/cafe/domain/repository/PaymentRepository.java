package com.atradius.cafe.domain.repository;

import com.atradius.cafe.domain.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface PaymentRepository {
    List<Payment> findByUser(String user);
}
