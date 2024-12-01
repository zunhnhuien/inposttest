package com.eu.inpost.inposttest.controller;

import com.eu.inpost.inposttest.service.CartCalculatorService;
import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.response.CalculatedCartDto;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@Validated
public class CartCalculateController {

    private final CartCalculatorService cartCalculatorService;

    public CartCalculateController(@Autowired CartCalculatorService cartCalculatorService) {
        this.cartCalculatorService = cartCalculatorService;
    }


    @PostMapping("/calculate")

    public ResponseEntity<CalculatedCartDto> calculate(@Valid @RequestBody CartDto cartDto) {

        final CalculatedCartDto calculatedCartDto = cartCalculatorService.calculateCart(cartDto);
        return ResponseEntity.ok(calculatedCartDto);
    }

    @ExceptionHandler(NotValidCartDtoException.class)
    public ResponseEntity<String> handleIllegalArgumentException(NotValidCartDtoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
