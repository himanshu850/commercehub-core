
package com.commercehub.strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.Product;
import com.commercehub.service.PricingService;

class DiscountStrategyTest {

    private final Product laptop = new PhysicalProduct(
        101, "HP Victus",
        new BigDecimal("899.99"), 10, 2.5);

    @Test
    void noDiscountShouldReturnFullPrice() {
        PricingService pricing =
            new PricingService(new NoDiscountStrategy());

        assertMoneyEquals("909.99",
            pricing.calculateFinalPrice(laptop));
    }

    @Test
    void percentageDiscountShouldReducePrice() {
        PricingService pricing = new PricingService(
            new PercentageDiscountStrategy(
                new BigDecimal("10")));

        assertMoneyEquals("819.99",
            pricing.calculateFinalPrice(laptop));
    }

    @Test
    void fixedDiscountShouldReducePrice() {
        PricingService pricing = new PricingService(
            new FixedDiscountStrategy(
                new BigDecimal("20.00")));

        assertMoneyEquals("889.99",
            pricing.calculateFinalPrice(laptop));
    }

    @Test
    void fixedDiscountCannotExceedProductPrice() {
        DiscountStrategy strategy =
            new FixedDiscountStrategy(
                new BigDecimal("2000"));

        assertMoneyEquals("899.99",
            strategy.calculateDiscount(
                laptop.getPrice()));
    }

    @Test
    void shouldRejectInvalidPercentage() {
        assertThrows(IllegalArgumentException.class, () ->
            new PercentageDiscountStrategy(
                new BigDecimal("120")));
    }

    @Test
    void shouldRejectNegativeFixedDiscount() {
        assertThrows(IllegalArgumentException.class, () ->
            new FixedDiscountStrategy(
                new BigDecimal("-5")));
    }

    private static void assertMoneyEquals(
            String expected, BigDecimal actual) {

        assertEquals(0,
            new BigDecimal(expected).compareTo(actual));
    }
}
