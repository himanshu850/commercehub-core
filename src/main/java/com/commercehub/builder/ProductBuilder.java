
package com.commercehub.builder;

import java.math.BigDecimal;

import com.commercehub.factory.ProductFactory;
import com.commercehub.model.Product;

public class ProductBuilder {

    private enum Type {
        PHYSICAL, DIGITAL
    }

    private final Type type;

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Double weightKg;
    private String downloadUrl;

    private ProductBuilder(Type type) {
        this.type = type;
    }

    public static ProductBuilder physical() {
        return new ProductBuilder(Type.PHYSICAL);
    }

    public static ProductBuilder digital() {
        return new ProductBuilder(Type.DIGITAL);
    }

    public ProductBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder weightKg(double weightKg) {
        this.weightKg = weightKg;
        return this;
    }

    public ProductBuilder downloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public Product build() {
        if (id == null || name == null ||
            price == null || quantity == null) {

            throw new IllegalStateException(
                "ID, name, price and quantity are required");
        }

        if (type == Type.PHYSICAL) {
            if (weightKg == null) {
                throw new IllegalStateException(
                    "Weight is required");
            }

            return ProductFactory.createPhysicalProduct(
                id, name, price, quantity, weightKg);
        }

        if (downloadUrl == null) {
            throw new IllegalStateException(
                "Download URL is required");
        }

        return ProductFactory.createDigitalProduct(
            id, name, price, quantity, downloadUrl);
    }
}
