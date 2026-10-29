
package com.commercehub.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.commercehub.model.DigitalProduct;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.Product;

class ProductFactoryTest {

    @Test
    void shouldCreatePhysicalProduct() {
        Product product = ProductFactory.createPhysicalProduct(
            101, "HP Victus",
            new BigDecimal("899.99"), 10, 2.5);

        assertAll(
            () -> assertInstanceOf(PhysicalProduct.class, product),
            () -> assertEquals(101, product.getId()),
            () -> assertEquals("HP Victus", product.getName()),
            () -> assertEquals(10, product.getQuantity()),
            () -> assertEquals(
                0, new BigDecimal("10.00")
                    .compareTo(product.getDeliveryCost()))
        );
    }

    @Test
    void shouldCreateDigitalProduct() {
        Product product = ProductFactory.createDigitalProduct(
            102, "Java E-Book",
            new BigDecimal("29.99"), 100,
            "https://example.com/book");

        assertInstanceOf(DigitalProduct.class, product);
        assertEquals("Digital", product.getProductType());
        assertEquals(
            0, BigDecimal.ZERO.compareTo(
                product.getDeliveryCost()));
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () ->
            ProductFactory.createPhysicalProduct(
                103, "Laptop",
                new BigDecimal("-100"), 5, 2.0));
    }
}
