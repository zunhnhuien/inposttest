package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DiscountValueValidator implements DtoValidator<CartDto> {
    private static final int MIN_VALUE_OF_DISCOUNT = 1;
    private static final String ERROR_MESSAGE = "remove discount cause minimum discount value is 1";

    @Override
    public void validate(CartDto cartDto) {
        if (CollectionUtils.isEmpty(cartDto.getAppliedDiscount())) {
            return;
        }

        boolean hasInvalidDiscount = cartDto.getAppliedDiscount().stream()
                .anyMatch(this::isInvalidDiscountValue);

        if (hasInvalidDiscount) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }
    }

    boolean isInvalidDiscountValue(DiscountDto discountDto) {
        return discountDto.getDiscountValue() < MIN_VALUE_OF_DISCOUNT;
    }
}
