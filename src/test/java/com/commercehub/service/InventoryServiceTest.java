
package com.commercehub.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.commercehub.exception.InsufficientStockException;
import com.commercehub.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository repository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(repository);
    }

    @Test
    void shouldPurchaseWhenStockIsAvailable() {
        when(repository.reserveStock(101, 2))
            .thenReturn(true);

        assertDoesNotThrow(
            () -> inventoryService.purchaseProduct(101, 2));

        verify(repository).reserveStock(101, 2);
    }

    @Test
    void shouldRejectInsufficientStock() {
        when(repository.reserveStock(101, 20))
            .thenReturn(false);

        assertThrows(InsufficientStockException.class,
            () -> inventoryService.purchaseProduct(101, 20));
    }

    @Test
    void shouldRejectZeroQuantity() {
        assertThrows(IllegalArgumentException.class,
            () -> inventoryService.purchaseProduct(101, 0));

        verifyNoInteractions(repository);
    }

    @Test
    void shouldRejectNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
            () -> inventoryService.purchaseProduct(101, -5));

        verifyNoInteractions(repository);
    }
}
