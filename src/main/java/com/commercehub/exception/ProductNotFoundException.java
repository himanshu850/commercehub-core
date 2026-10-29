
package com.commercehub.exception;

public class ProductNotFoundException
        extends RuntimeException {

    public ProductNotFoundException(int id) {
        super("Product with ID " + id +
              " was not found.");
    }
}
