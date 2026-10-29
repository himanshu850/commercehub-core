
package com.commercehub.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.commercehub.model.Product;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.DigitalProduct;
import com.commercehub.repository.InMemoryProductRepository;

public class CollectionDemo {

    public static void main(String[] args) {

        Product laptop = new PhysicalProduct(
            103, "HP Victus",
            new BigDecimal("899.99"), 10, 2.5);

        Product ebook = new DigitalProduct(
            101, "Java E-Book",
            new BigDecimal("29.99"), 100,
            "https://example.com/book");

        Product keyboard = new PhysicalProduct(
            102, "Keyboard",
            new BigDecimal("49.99"), 25, 0.8);

        // 1. ArrayList
        List<Product> productList = new ArrayList<>();

        productList.add(laptop);
        productList.add(ebook);
        productList.add(keyboard);

        System.out.println("=== ARRAYLIST ===");

        for (Product p : productList) {
            System.out.println(p.getName());
        }

        // 2. HashMap
        Map<Integer, Product> productMap =
                new HashMap<>();

        for (Product p : productList) {
            productMap.put(p.getId(), p);
        }

        System.out.println("\n=== HASHMAP ===");
        System.out.println(
            "Product 101: " +
            productMap.get(101).getName());

        // 3. HashSet
        Set<String> productTypes = new HashSet<>();

        for (Product p : productList) {
            productTypes.add(p.getProductType());
        }

        System.out.println("\n=== HASHSET ===");
        System.out.println("Unique types: " +
                productTypes.size());

        for (String type : productTypes) {
            System.out.println(type);
        }

        // 4. TreeMap
        Map<Integer, Product> sortedMap =
                new TreeMap<>(productMap);

        System.out.println("\n=== TREEMAP ===");

        for (Map.Entry<Integer, Product> entry :
                sortedMap.entrySet()) {

            System.out.println(
                entry.getKey() + " -> " +
                entry.getValue().getName());
        }

        // 5. Comparable
        Collections.sort(productList);

        System.out.println("\n=== COMPARABLE: BY ID ===");

        for (Product p : productList) {
            System.out.println(
                p.getId() + " - " + p.getName());
        }

        // 6. Comparator
        productList.sort(
            ProductComparators.BY_PRICE_ASC);

        System.out.println(
            "\n=== COMPARATOR: BY PRICE ===");

        for (Product p : productList) {
            System.out.println(
                p.getName() + " - $" + p.getPrice());
        }

        // 7. Generic repository
        InMemoryProductRepository repository =
                new InMemoryProductRepository();

        for (Product p : productList) {
            repository.save(p);
        }

        System.out.println(
            "\n=== GENERIC REPOSITORY ===");

        repository.findById(101)
            .ifPresent(p -> System.out.println(
                "Found: " + p.getName()));

        System.out.println(
            "\n=== REPOSITORY TREEMAP REPORT ===");

        for (Product p : repository.findAllSortedById()) {
            System.out.println(
                p.getId() + " - " + p.getName());
        }
    }
}
