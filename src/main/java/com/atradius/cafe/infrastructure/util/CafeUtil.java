package com.atradius.cafe.infrastructure.util;

import java.math.BigDecimal;

public class CafeUtil {

    public static boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

}
