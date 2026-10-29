
package com.commercehub.service;

import com.commercehub.exception.InsufficientStockException;
import com.commercehub.repository.ProductRepository;

public class InventoryService {

    private final ProductRepository repository;

    public InventoryService(ProductRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException(
                "Repository is required");
        }
        this.repository = repository;
    }

    public void purchaseProduct(int productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Purchase quantity must be positive");
        }

        boolean reserved =
            repository.reserveStock(productId, quantity);

        if (!reserved) {
            throw new InsufficientStockException(
                "Insufficient stock for product ID " + productId);
        }
    }
}
