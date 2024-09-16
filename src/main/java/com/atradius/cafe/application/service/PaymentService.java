package com.atradius.cafe.application.service;

import com.atradius.cafe.application.dto.UserPaymentDTO;
import com.atradius.cafe.application.mapper.UserPaymentDTOMapper;
import com.atradius.cafe.domain.model.Payment;
import com.atradius.cafe.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public UserPaymentDTO calculatePaidUser(String user) {

        List<Payment> userPayments = paymentRepository.findByUser(user);

        BigDecimal totalPaid = userPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        UserPaymentDTO userPaymentDTO = UserPaymentDTOMapper.INSTANCE.toUserPaymentDTO(user, totalPaid);

        return userPaymentDTO;
    }
}
