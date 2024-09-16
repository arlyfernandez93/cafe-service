package com.atradius.cafe.infrastructure.controller;

import com.atradius.cafe.application.dto.UserPaymentDTO;
import com.atradius.cafe.application.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment Management")
@RequestMapping("/v1/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get total payment for a user",
            description = "Returns the total amount of payments made by the specified user.")
    @GetMapping("/user/{user}")
    public UserPaymentDTO getUserPayment(
            @Parameter(description = "user for which to retrieve payment information", required = true)
            @PathVariable String user) {
        return paymentService.calculatePaidUser(user);
    }

}
