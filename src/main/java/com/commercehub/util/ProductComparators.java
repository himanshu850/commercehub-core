
package com.commercehub.util;

import java.util.Comparator;
import com.commercehub.model.Product;

public final class ProductComparators {

    private ProductComparators() {
        // Prevent creating utility class objects.
    }

    public static final Comparator<Product> BY_PRICE_ASC =
            Comparator.comparing(Product::getPrice)
                      .thenComparingInt(Product::getId);

    public static final Comparator<Product> BY_PRICE_DESC =
            Comparator.comparing(
                Product::getPrice,
                Comparator.reverseOrder()
            ).thenComparingInt(Product::getId);

    public static final Comparator<Product> BY_NAME =
            Comparator.comparing(
                Product::getName,
                String.CASE_INSENSITIVE_ORDER
            ).thenComparingInt(Product::getId);

    public static final Comparator<Product> BY_STOCK =
            Comparator.comparingInt(Product::getQuantity)
                      .thenComparingInt(Product::getId);
}
