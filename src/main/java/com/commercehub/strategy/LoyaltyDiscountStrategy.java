
package com.commercehub.strategy;

import java.math.BigDecimal;

public class LoyaltyDiscountStrategy
        implements DiscountStrategy {

    private final PercentageDiscountStrategy delegate =
        new PercentageDiscountStrategy(
            new BigDecimal("15"));

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return delegate.calculateDiscount(amount);
    }
}
