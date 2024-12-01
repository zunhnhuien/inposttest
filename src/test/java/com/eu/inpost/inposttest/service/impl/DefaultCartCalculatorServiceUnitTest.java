package com.eu.inpost.inposttest.service.impl;

import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.response.CalculatedCartDto;
import com.eu.inpost.inposttest.service.dto.validator.CartDtoValidatorUtil;
import com.eu.inpost.inposttest.service.dto.validator.expetion.NotValidCartDtoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

class DefaultCartCalculatorServiceUnitTest {
    private static final String CART_WITH_APPLIED_DISCOUNT_PAYLOAD = """
            {
                         "products": [
                           {
                             "uuid": "P1",
                             "qty": "4",
                             "price":"40"
                           },
                           {
                             "uuid": "P2",
                             "qty": "2",
                             "price":"10"
                           }
                         ],
                         "appliedDiscount": [
                         {
                           "type": "percentage-based",
                           "productUUID": "P1",
                           "threshold": "1",
                           "discountValue": "50"
                         },
                         {
                           "type": "amount-based",
                           "productUUID": "P2",
                           "threshold": "1",
                           "discountValue":"12"
                         }
                       ]
                                  
            }
                       """;


    private static final String CART_WITH_WITH_NO_DISCOUNT_PAYLOAD = """
            {
                         "products": [
                           {
                             "uuid": "P1",
                             "qty": "4",
                             "price":"40"
                           },
                           {
                             "uuid": "P2",
                             "qty": "2",
                             "price":"10"
                           }
                         ]
                                  
            }
                       """;

    private static final String CART_WITH_NOT_VALID_DISCOUNT_PAYLOAD = """
            {
                         "products": [
                           {
                             "uuid": "P1",
                             "qty": "4",
                             "price":"40"
                           },
                           {
                             "uuid": "P2",
                             "qty": "2",
                             "price":"10"
                           }
                         ],
                         "appliedDiscount": [
                         {
                           "type": "percentage-based",
                           "productUUID": "P1",
                           "threshold": "1",
                           "discountValue": "300"
                         },
                         {
                           "type": "amount-based",
                           "productUUID": "P2",
                           "threshold": "1",
                           "discountValue":"12"
                         }
                       ]
                                  
            }
                       """;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private DefaultCartCalculatorService cartCalculatorService;

    @Mock
    private CartDtoValidatorUtil cartDtoValidatorUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateCart_shouldReturnCartWithCalculatedDiscounts() throws JsonProcessingException {
        final CartDto cartDto = objectMapper.readValue(CART_WITH_APPLIED_DISCOUNT_PAYLOAD, CartDto.class);
        final CalculatedCartDto result = cartCalculatorService.calculateCart(cartDto);

        assertPrice(result.getCartFinalPrice(), new BigDecimal(92));
        assertPrice(result.getDiscount(), new BigDecimal(88));
        assertPrice(result.getCartBasePrice(), new BigDecimal(180));

        assertTrue(true);

    }

    @Test
    void calculateCart_shouldReturnNotValidCartDtoException() throws JsonProcessingException {
        final CartDto cartDto = objectMapper.readValue(CART_WITH_NOT_VALID_DISCOUNT_PAYLOAD, CartDto.class);

        doThrow(new NotValidCartDtoException()).when(cartDtoValidatorUtil).validate(cartDto);
        assertThrows(NotValidCartDtoException.class,()->cartCalculatorService.calculateCart(cartDto));
    }

    @Test
    void calculateCart_shouldReturnCartWithNoDiscounts() throws JsonProcessingException {
        final CartDto cartDto = objectMapper.readValue(CART_WITH_WITH_NO_DISCOUNT_PAYLOAD, CartDto.class);
        final CalculatedCartDto result = cartCalculatorService.calculateCart(cartDto);

        assertPrice(result.getCartFinalPrice(), new BigDecimal(180));
        assertPrice(result.getDiscount(), new BigDecimal(0));
        assertPrice(result.getCartBasePrice(), new BigDecimal(180));

        assertTrue(true);
    }


    private void assertPrice(BigDecimal result, BigDecimal expected) {
        assertEquals(0, result.compareTo(expected));
    }
}