
package com.commercehub.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.commercehub.model.Product;
import com.commercehub.strategy.DiscountStrategy;

public class PricingService {

    private final DiscountStrategy strategy;

    public PricingService(DiscountStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException(
                "Discount strategy is required");
        }
        this.strategy = strategy;
    }

    public BigDecimal calculateFinalPrice(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null");
        }

        BigDecimal basePrice = product.getPrice();

        BigDecimal discount =
            strategy.calculateDiscount(basePrice);

        if (discount == null ||
            discount.signum() < 0 ||
            discount.compareTo(basePrice) > 0) {

            throw new IllegalStateException(
                "Invalid discount returned by strategy");
        }

        return basePrice
            .subtract(discount)
            .add(product.getDeliveryCost())
            .setScale(2, RoundingMode.HALF_UP);
    }
}
