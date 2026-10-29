
package com.commercehub.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.commercehub.exception.ProductNotFoundException;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.Product;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();

        repository.save(new PhysicalProduct(
            101, "Laptop",
            new BigDecimal("900.00"), 10, 2.0));
    }

    @Test
    void shouldFindProductById() {
        Product product = repository.findById(101)
            .orElseThrow();

        assertEquals("Laptop", product.getName());
    }

    @Test
    void shouldReturnEmptyForMissingProduct() {
        assertTrue(repository.findById(999).isEmpty());
    }

    @Test
    void shouldRejectDuplicateProductId() {
        Product duplicate = new PhysicalProduct(
            101, "Another Laptop",
            new BigDecimal("1000"), 5, 2.0);

        assertThrows(IllegalArgumentException.class,
            () -> repository.save(duplicate));
    }

    @Test
    void shouldReserveAvailableStock() {
        boolean reserved = repository.reserveStock(101, 3);

        assertTrue(reserved);
        assertEquals(7,
            repository.findById(101)
                .orElseThrow().getQuantity());
    }

    @Test
    void shouldRejectPurchaseExceedingStock() {
        boolean reserved = repository.reserveStock(101, 20);

        assertFalse(reserved);
        assertEquals(10,
            repository.findById(101)
                .orElseThrow().getQuantity());
    }

    @Test
    void shouldUpdateStock() {
        repository.setStock(101, 25);

        assertEquals(25,
            repository.findById(101)
                .orElseThrow().getQuantity());
    }

    @Test
    void shouldDeleteProduct() {
        repository.deleteById(101);

        assertTrue(repository.findById(101).isEmpty());
    }

    @Test
    void shouldThrowForMissingStockUpdate() {
        assertThrows(ProductNotFoundException.class,
            () -> repository.setStock(999, 10));
    }
}
