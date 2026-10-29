
package com.commercehub.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.commercehub.exception.ProductNotFoundException;
import com.commercehub.model.Product;

public class InMemoryProductRepository
        implements ProductRepository {

    private final Map<Integer, Product> products =
            new ConcurrentHashMap<>();

    @Override
    public synchronized void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null");
        }

        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException(
                "Product ID already exists: " + product.getId());
        }

        products.put(product.getId(), product);
    }

    @Override
    public synchronized Optional<Product> findById(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public synchronized List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public synchronized void deleteById(Integer id) {
        products.remove(id);
    }

    @Override
    public synchronized boolean reserveStock(
            int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be positive");
        }

        Product product = products.get(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        if (product.getQuantity() < quantity) {
            return false;
        }

        product.setQuantity(
            product.getQuantity() - quantity);

        return true;
    }

    @Override
    public synchronized void setStock(
            int productId, int quantity) {

        Product product = products.get(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        product.setQuantity(quantity);
    }

    public synchronized List<Product> findAllSortedById() {
        Map<Integer, Product> sorted =
            new TreeMap<>(products);

        return new ArrayList<>(sorted.values());
    }
}
