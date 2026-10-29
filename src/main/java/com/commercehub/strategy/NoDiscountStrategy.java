
package com.commercehub.strategy;

import java.math.BigDecimal;

public class NoDiscountStrategy
        implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
