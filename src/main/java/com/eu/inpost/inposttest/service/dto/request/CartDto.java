package com.eu.inpost.inposttest.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartDto {

    @NotEmpty(message = "product is mandatory parameter")
    @NotNull(message = "product is mandatory parameter")
    private List<CartEntryDto> products;
    private List<DiscountDto> appliedDiscount = new ArrayList<>();

    CartDto() {
    }


    public List<CartEntryDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartEntryDto> products) {
        this.products = products;
    }

    public List<DiscountDto> getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(List<DiscountDto> appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }
}
