package com.atradius.cafe.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class UserPaymentDTO {

    private String user;
    private BigDecimal paid;

}
