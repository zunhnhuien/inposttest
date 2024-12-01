package com.eu.inpost.inposttest.service.dto.request;

import jakarta.validation.constraints.NotNull;

public class DiscountDto {

    @NotNull
    private DiscountTypeDto type;
    @NotNull
    private String productUUID;
    private long threshold;
    private double discountValue;

    public DiscountDto() {
    }

    public DiscountTypeDto getType() {
        return type;
    }

    public void setType(DiscountTypeDto type) {
        this.type = type;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

}
