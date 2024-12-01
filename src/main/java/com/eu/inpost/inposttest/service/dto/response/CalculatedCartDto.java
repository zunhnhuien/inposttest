package com.eu.inpost.inposttest.service.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class CalculatedCartDto {
    private BigDecimal cartBasePrice;
    private BigDecimal discount;
    private BigDecimal cartFinalPrice;

    private List<CalculatedCartEntryDto> calculatedCartEntryDtoList;

    public CalculatedCartDto() {
    }

    public CalculatedCartDto(BigDecimal cartBasePrice, BigDecimal cartFinalPrice, BigDecimal discount, List<CalculatedCartEntryDto> calculatedCartEntryDtoList) {
        this.cartBasePrice = cartBasePrice;
        this.discount = discount;
        this.cartFinalPrice = cartFinalPrice;
        this.calculatedCartEntryDtoList = calculatedCartEntryDtoList;
    }

    public BigDecimal getCartBasePrice() {
        return cartBasePrice;
    }

    public void setCartBasePrice(BigDecimal cartBasePrice) {
        this.cartBasePrice = cartBasePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public List<CalculatedCartEntryDto> getCalculatedCartEntryList() {
        return calculatedCartEntryDtoList;
    }

    public void setCalculatedCartEntryList(List<CalculatedCartEntryDto> calculatedCartEntryDtoList) {
        this.calculatedCartEntryDtoList = calculatedCartEntryDtoList;
    }

    public BigDecimal getCartFinalPrice() {
        return cartFinalPrice;
    }

    public void setCartFinalPrice(BigDecimal cartFinalPrice) {
        this.cartFinalPrice = cartFinalPrice;
    }
}
