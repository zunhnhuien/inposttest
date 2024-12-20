package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountTypeDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PercentageBasedDiscountValidator implements DtoValidator<CartDto> {
    private static final int MAX_PERCENTAGE_VALUE = 99;
    private static final String ERROR_MESSAGE = "percentage based discount can not has discount value more then 99";

    @Override
    public void validate(CartDto cartDto) {
        if (CollectionUtils.isEmpty(cartDto.getAppliedDiscount())) {
            return;
        }

        boolean hasInvalidDiscount = cartDto.getAppliedDiscount().stream()
                .anyMatch(this::isInvalidPercentageBasedDiscount);

        if (hasInvalidDiscount) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }
    }

    private boolean isInvalidPercentageBasedDiscount(DiscountDto discountDto) {
        return DiscountTypeDto.PERCENTAGE_BASED.equals(discountDto.getType())
                && discountDto.getDiscountValue() > MAX_PERCENTAGE_VALUE;
    }
}
