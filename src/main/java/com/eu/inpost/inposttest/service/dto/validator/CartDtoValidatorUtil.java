package com.eu.inpost.inposttest.service.dto.validator;


import com.eu.inpost.inposttest.service.dto.request.CartDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDtoValidatorUtil {
    private final List<DtoValidator<CartDto>> validators;

    public CartDtoValidatorUtil(List<DtoValidator<CartDto>> validators) {
        this.validators = validators;
    }

    public void validate(CartDto cartDto) {
        validators
                .stream()
                .forEach(validator -> validator.validate(cartDto));
    }
}
