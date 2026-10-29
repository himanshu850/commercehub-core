
package com.commercehub.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal calculateDiscount(BigDecimal amount);
}
