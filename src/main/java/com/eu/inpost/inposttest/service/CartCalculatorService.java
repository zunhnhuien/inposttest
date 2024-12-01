package com.eu.inpost.inposttest.service;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.response.CalculatedCartDto;

public interface CartCalculatorService {
    CalculatedCartDto calculateCart(final CartDto cartDto);
}
