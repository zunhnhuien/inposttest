package com.eu.inpost.inposttest.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountTypeDto {
    PERCENTAGE_BASED("percentage-based"),
    AMOUNT_BASED("amount-based");

    private final String value;

    DiscountTypeDto(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DiscountTypeDto fromValue(String value) {
        for (DiscountTypeDto type : DiscountTypeDto.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DiscountType value: " + value);
    }
}
