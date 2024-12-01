 package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PriceValueValidator implements DtoValidator<CartDto> {
    private static final BigDecimal MIN_VALUE_OF_PRICE = new BigDecimal(1);
    private static final String ERROR_MESSAGE = "minimal value of price is 1";

    @Override
    public void validate(CartDto cartDto) {
        boolean isValid = cartDto.getProducts().stream().anyMatch(c -> c.getPrice().compareTo(MIN_VALUE_OF_PRICE) < 0);
        if (isValid) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }


    }
}
