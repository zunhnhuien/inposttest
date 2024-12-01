package com.eu.inpost.inposttest.service.dto.validator.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.CartEntryDto;
import com.eu.inpost.inposttest.service.dto.validator.DtoValidator;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UniqueProductValidator implements DtoValidator<CartDto> {

    private static final String ERROR_MESSAGE = "product entry must have unique product UUID";

    @Override
    public void validate(CartDto cartDto) {
        final List<CartEntryDto> cartEntryDtoList = cartDto.getProducts();

        List<String> uuidList = cartEntryDtoList.stream()
                .map(CartEntryDto::getUuid)
                .toList();

        Set<String> uuidSet = new HashSet<>(uuidList);
        if (uuidSet.size() != uuidList.size()) {
            throw new NotValidCartDtoException(ERROR_MESSAGE);
        }
    }
}
