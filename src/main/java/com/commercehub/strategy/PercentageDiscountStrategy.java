
package com.commercehub.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscountStrategy
        implements DiscountStrategy {

    private final BigDecimal percentage;

    public PercentageDiscountStrategy(BigDecimal percentage) {
        if (percentage == null ||
            percentage.compareTo(BigDecimal.ZERO) < 0 ||
            percentage.compareTo(new BigDecimal("100")) > 0) {

            throw new IllegalArgumentException(
                "Percentage must be between 0 and 100");
        }

        this.percentage = percentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(percentage)
            .divide(new BigDecimal("100"), 2,
                    RoundingMode.HALF_UP);
    }
}
