package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountTypeDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;

@Component
public class PercentageBasedDiscountValidator implements DtoValidator<CartDto> {
    private static final int MAX_PERCENTAGE_VALUE = 100;
    private static final String ERROR_MESSAGE = "percentage based discount can not has discount value more then 100";

    @Override
    public void validate(CartDto cartDto) {

        boolean hasInvalidDiscount = cartDto.getAppliedDiscount().stream()
                .anyMatch(discount -> !isValidPercentageBasedDiscount(discount));

        if (hasInvalidDiscount) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }
    }


    boolean isValidPercentageBasedDiscount(DiscountDto discountDto) {
        return discountDto.getDiscountValue() < MAX_PERCENTAGE_VALUE
                && DiscountTypeDto.PERCENTAGE_BASED.equals(discountDto.getType());
    }
}
