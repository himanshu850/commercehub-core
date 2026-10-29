
package com.commercehub.model;

import java.math.BigDecimal;

public class PhysicalProduct extends Product {

    private double weightKg;

    public PhysicalProduct(int id, String name,
                           BigDecimal price,
                           int quantity, double weightKg) {

        super(id, name, price, quantity);
        setWeightKg(weightKg);
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        if (!Double.isFinite(weightKg) || weightKg <= 0) {
            throw new IllegalArgumentException(
                "Weight must be a positive finite number");
        }
        this.weightKg = weightKg;
    }

    @Override
    public String getProductType() {
        return "Physical";
    }

    @Override
    public BigDecimal getDeliveryCost() {
        BigDecimal baseCost = new BigDecimal("5.00");
        BigDecimal costPerKg = new BigDecimal("2.00");

        return baseCost.add(
            costPerKg.multiply(
                BigDecimal.valueOf(weightKg)
            )
        ).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public String getDeliveryMethod() {
        return "Courier Shipping";
    }
}
