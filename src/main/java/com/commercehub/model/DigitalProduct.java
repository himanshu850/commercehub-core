
package com.commercehub.model;

import java.math.BigDecimal;

public class DigitalProduct extends Product {

    private String downloadUrl;

    public DigitalProduct(int id, String name,
                          BigDecimal price,
                          int quantity, String downloadUrl) {

        super(id, name, price, quantity);
        setDownloadUrl(downloadUrl);
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        if (downloadUrl == null ||
            downloadUrl.isBlank()) {
            throw new IllegalArgumentException(
                "Download URL cannot be empty");
        }
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String getProductType() {
        return "Digital";
    }

    @Override
    public BigDecimal getDeliveryCost() {
        return new BigDecimal("0.00");
    }

    @Override
    public String getDeliveryMethod() {
        return "Electronic Download";
    }
}
