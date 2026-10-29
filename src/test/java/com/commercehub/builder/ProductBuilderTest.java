
package com.commercehub.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.commercehub.model.DigitalProduct;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.Product;

class ProductBuilderTest {

    @Test
    void shouldBuildPhysicalProduct() {
        Product product = ProductBuilder.physical()
            .id(101)
            .name("Keyboard")
            .price(new BigDecimal("49.99"))
            .quantity(20)
            .weightKg(0.8)
            .build();

        assertInstanceOf(PhysicalProduct.class, product);
        assertEquals("Keyboard", product.getName());
        assertEquals(20, product.getQuantity());
    }

    @Test
    void shouldBuildDigitalProduct() {
        Product product = ProductBuilder.digital()
            .id(102)
            .name("Java Course")
            .price(new BigDecimal("79.99"))
            .quantity(50)
            .downloadUrl("https://example.com/course")
            .build();

        assertInstanceOf(DigitalProduct.class, product);
        assertEquals("Java Course", product.getName());
    }

    @Test
    void shouldRejectMissingWeight() {
        ProductBuilder builder = ProductBuilder.physical()
            .id(103)
            .name("Mouse")
            .price(new BigDecimal("25.00"))
            .quantity(10);

        assertThrows(IllegalStateException.class,
            builder::build);
    }

    @Test
    void shouldRejectMissingDownloadUrl() {
        ProductBuilder builder = ProductBuilder.digital()
            .id(104)
            .name("E-Book")
            .price(new BigDecimal("20.00"))
            .quantity(100);

        assertThrows(IllegalStateException.class,
            builder::build);
    }

    @Test
    void shouldRejectBlankProductName() {
        ProductBuilder builder = ProductBuilder.physical()
            .id(105)
            .name(" ")
            .price(new BigDecimal("30.00"))
            .quantity(5)
            .weightKg(1.0);

        assertThrows(IllegalArgumentException.class,
            builder::build);
    }
}
