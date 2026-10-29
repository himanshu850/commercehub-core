
package com.commercehub.factory;

import java.math.BigDecimal;

import com.commercehub.model.Product;
import com.commercehub.model.PhysicalProduct;
import com.commercehub.model.DigitalProduct;

public final class ProductFactory {

    private ProductFactory() {
    }

    public static Product createPhysicalProduct(
            int id, String name, BigDecimal price,
            int quantity, double weightKg) {

        return new PhysicalProduct(
            id, name, price, quantity, weightKg);
    }

    public static Product createDigitalProduct(
            int id, String name, BigDecimal price,
            int quantity, String downloadUrl) {

        return new DigitalProduct(
            id, name, price, quantity, downloadUrl);
    }
}
