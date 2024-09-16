package com.atradius.cafe.infrastructure.controller;

import com.atradius.cafe.application.dto.UserDebtDTO;
import com.atradius.cafe.application.service.DebtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Debt Management")
@RequestMapping("v1/debts")
@RestController
public class DebtController {
    private final DebtService debtService;


    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @Operation(summary = "Get total outstanding debt for a user",
            description = "Returns the outstanding debt amount of the specified user based on their orders and payments.")
    @GetMapping("/user/{user}")
    public UserDebtDTO getUserDebt(
            @Parameter(description = "user for which to retrieve debt information", required = true)
            @PathVariable String user) {
        return debtService.calculateDebtPerCustomer(user);
    }
}
