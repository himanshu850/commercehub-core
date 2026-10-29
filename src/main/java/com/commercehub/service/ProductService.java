
package com.commercehub.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.commercehub.exception.ProductNotFoundException;
import com.commercehub.factory.ProductFactory;
import com.commercehub.model.Product;
import com.commercehub.repository.ProductRepository;
import com.commercehub.strategy.DiscountStrategy;
import com.commercehub.util.ProductComparators;

public class ProductService {

    private final ProductRepository repository;
    private final InventoryService inventoryService;

    public ProductService(ProductRepository repository) {
        this(repository, new InventoryService(repository));
    }

    public ProductService(
            ProductRepository repository,
            InventoryService inventoryService) {

        if (repository == null || inventoryService == null) {
            throw new IllegalArgumentException(
                "Dependencies cannot be null");
        }

        this.repository = repository;
        this.inventoryService = inventoryService;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null");
        }

        repository.save(product);
    }

    public void addProduct(
            int id, String name, BigDecimal price,
            int quantity) {

        addPhysicalProduct(id, name, price, quantity, 1.0);
    }

    public void addPhysicalProduct(
            int id, String name, BigDecimal price,
            int quantity, double weightKg) {

        Product product =
            ProductFactory.createPhysicalProduct(
                id, name, price, quantity, weightKg);

        addProduct(product);
    }

    public void addDigitalProduct(
            int id, String name, BigDecimal price,
            int quantity, String downloadUrl) {

        Product product =
            ProductFactory.createDigitalProduct(
                id, name, price, quantity, downloadUrl);

        addProduct(product);
    }

    public Product getProduct(int id) {
        return repository.findById(id)
            .orElseThrow(
                () -> new ProductNotFoundException(id));
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updateStock(int id, int quantity) {
        repository.setStock(id, quantity);
    }

    public void purchaseProduct(int id, int amount) {
        inventoryService.purchaseProduct(id, amount);
    }

    public void deleteProduct(int id) {
        getProduct(id);
        repository.deleteById(id);
    }

    public BigDecimal calculateTotalPrice(int id) {
        Product product = getProduct(id);

        return product.getPrice()
            .add(product.getDeliveryCost());
    }

    public BigDecimal calculateDiscountedPrice(
            int id, DiscountStrategy strategy) {

        Product product = getProduct(id);
        PricingService pricingService =
            new PricingService(strategy);

        return pricingService.calculateFinalPrice(product);
    }

    public List<Product> getProductsSortedById() {
        List<Product> result =
            new ArrayList<>(repository.findAll());

        Collections.sort(result);
        return result;
    }

    public List<Product> getProductsSortedByPrice() {
        return sorted(ProductComparators.BY_PRICE_ASC);
    }

    public List<Product> getProductsSortedByPriceDesc() {
        return sorted(ProductComparators.BY_PRICE_DESC);
    }

    public List<Product> getProductsSortedByName() {
        return sorted(ProductComparators.BY_NAME);
    }

    public List<Product> getProductsSortedByStock() {
        return sorted(ProductComparators.BY_STOCK);
    }

    private List<Product> sorted(
            Comparator<Product> comparator) {

        List<Product> result =
            new ArrayList<>(repository.findAll());

        result.sort(comparator);
        return result;
    }

    public Set<String> getProductTypes() {
        Set<String> types = new HashSet<>();

        for (Product product : repository.findAll()) {
            types.add(product.getProductType());
        }

        return types;
    }

    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(
                "Search keyword cannot be empty");
        }

        String search =
            keyword.toLowerCase(Locale.ROOT);

        return repository.findAll().stream()
            .filter(p -> p.getName()
                .toLowerCase(Locale.ROOT)
                .contains(search))
            .sorted()
            .toList();
    }

    public List<Product> filterByPrice(
            BigDecimal min, BigDecimal max) {

        if (min == null || max == null ||
            min.signum() < 0 ||
            min.compareTo(max) > 0) {

            throw new IllegalArgumentException(
                "Invalid price range");
        }

        return repository.findAll().stream()
            .filter(p ->
                p.getPrice().compareTo(min) >= 0)
            .filter(p ->
                p.getPrice().compareTo(max) <= 0)
            .sorted(ProductComparators.BY_PRICE_ASC)
            .toList();
    }

    public BigDecimal calculateInventoryValue() {
        return repository.findAll().stream()
            .map(p -> p.getPrice().multiply(
                BigDecimal.valueOf(p.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
