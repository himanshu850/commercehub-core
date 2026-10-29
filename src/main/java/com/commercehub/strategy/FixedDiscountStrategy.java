
package com.commercehub.strategy;

import java.math.BigDecimal;

public class FixedDiscountStrategy
        implements DiscountStrategy {

    private final BigDecimal discountAmount;

    public FixedDiscountStrategy(BigDecimal discountAmount) {
        if (discountAmount == null ||
            discountAmount.signum() < 0) {

            throw new IllegalArgumentException(
                "Discount cannot be negative");
        }

        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return discountAmount.min(amount);
    }
}
