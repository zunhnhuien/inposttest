package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UniqueDiscountValidator implements DtoValidator<CartDto> {
    private static final String ERROR_MESSAGE = "product entry can have only one applied discount";

    @Override
    public void validate(CartDto cartDto) {
        final List<DiscountDto> cartEntryDtoList = cartDto.getAppliedDiscount();

        final List<String> uuidList = cartEntryDtoList.stream()
                .map(DiscountDto::getProductUUID)
                .toList();

        final Set<String> uuidSet = new HashSet<>(uuidList);
        if (uuidSet.size() != uuidList.size()) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }
    }
}
