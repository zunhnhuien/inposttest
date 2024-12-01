package com.eu.inpost.inposttest.service.dto.response;

import java.math.BigDecimal;

public class CalculatedCartEntryDto {
    private String uuid;
    private BigDecimal basePrice;
    private BigDecimal discount;
    private BigDecimal finalPrice;

    public CalculatedCartEntryDto() {
    }

    public CalculatedCartEntryDto(String uuid, BigDecimal basePrice, BigDecimal discount, BigDecimal finalPrice) {
        this.uuid = uuid;
        this.basePrice = basePrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
