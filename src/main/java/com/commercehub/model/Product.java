
package com.commercehub.model;

import java.math.BigDecimal;
import com.commercehub.interfaces.Deliverable;

public abstract class Product
        implements Deliverable, Comparable<Product> {

    private final int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public Product(int id, String name,
                   BigDecimal price, int quantity) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Product ID must be positive");
        }

        this.id = id;
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Product name cannot be empty");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException(
                "Price cannot be negative");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public abstract String getProductType();

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Product [id=" + id +
               ", type=" + getProductType() +
               ", name=" + name +
               ", price=$" + price +
               ", quantity=" + quantity +
               ", delivery=$" + getDeliveryCost() +
               ", method=" + getDeliveryMethod() + "]";
    }
}
