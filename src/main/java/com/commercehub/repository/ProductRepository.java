
package com.commercehub.repository;

import com.commercehub.model.Product;

public interface ProductRepository
        extends GenericRepository<Product, Integer> {

    boolean reserveStock(int productId, int quantity);

    void setStock(int productId, int quantity);
}
