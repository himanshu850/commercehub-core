
package com.commercehub.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.commercehub.exception.ProductNotFoundException;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.Product;
import com.commercehub.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    private ProductService service;
    private Product laptop;

    @BeforeEach
    void setUp() {
        service = new ProductService(repository);

        laptop = new PhysicalProduct(
            101, "HP Victus",
            new BigDecimal("899.99"), 10, 2.5);
    }

    @Test
    void shouldAddProduct() {
        service.addProduct(laptop);

        verify(repository).save(laptop);
    }

    @Test
    void shouldFindProduct() {
        when(repository.findById(101))
            .thenReturn(Optional.of(laptop));

        Product result = service.getProduct(101);

        assertEquals("HP Victus", result.getName());
        verify(repository).findById(101);
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        when(repository.findById(999))
            .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
            () -> service.getProduct(999));
    }

    @Test
    void shouldDelegateStockUpdate() {
        service.updateStock(101, 20);

        verify(repository).setStock(101, 20);
    }

    @Test
    void shouldSortProductsByPrice() {
        Product keyboard = new PhysicalProduct(
            102, "Keyboard",
            new BigDecimal("49.99"), 20, 0.8);

        when(repository.findAll())
            .thenReturn(List.of(laptop, keyboard));

        List<Product> result =
            service.getProductsSortedByPrice();

        assertEquals(102, result.get(0).getId());
        assertEquals(101, result.get(1).getId());
    }

    @Test
    void shouldCalculateInventoryValue() {
        when(repository.findAll())
            .thenReturn(List.of(laptop));

        BigDecimal value =
            service.calculateInventoryValue();

        assertEquals(0,
            new BigDecimal("8999.90")
                .compareTo(value));
    }

    @Test
    void shouldRejectNullProduct() {
        assertThrows(IllegalArgumentException.class,
            () -> service.addProduct((Product) null));

        verify(repository, never()).save(any());
    }
}
