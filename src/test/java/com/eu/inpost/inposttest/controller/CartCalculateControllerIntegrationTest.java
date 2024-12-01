package com.eu.inpost.inposttest.controller;

import com.eu.inpost.inposttest.service.dto.response.CalculatedCartDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartCalculateControllerIntegrationTest {
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


    private static final String CART_WITH_NO_DISCOUNT_PAYLOAD = """
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


    private static final String CART_WITH_NOT_VALID_DATA_PAYLOAD = """
            {
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
    private static final String CALCULATION_API = "/cart/calculate";
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testRequestWithDiscount_shouldReturnCartWithCalculatedDiscounts() throws Exception {
        String response = fetchCalculationApi(CART_WITH_APPLIED_DISCOUNT_PAYLOAD);
        CalculatedCartDto result = objectMapper.readValue(response, CalculatedCartDto.class);

        assertPrice(result.getCartFinalPrice(), new BigDecimal(92));
        assertPrice(result.getDiscount(), new BigDecimal(88));
        assertPrice(result.getCartBasePrice(), new BigDecimal(180));
    }

    @Test
    void testRequestWithNoDiscount_shouldReturnCartWithCalculatedWithNoDiscount() throws Exception {
        String response = fetchCalculationApi(CART_WITH_NO_DISCOUNT_PAYLOAD);
        CalculatedCartDto result = objectMapper.readValue(response, CalculatedCartDto.class);

        assertPrice(result.getCartFinalPrice(), new BigDecimal(180));
        assertPrice(result.getDiscount(), new BigDecimal(0));
        assertPrice(result.getCartBasePrice(), new BigDecimal(180));
    }


    @Test
    void testRequestWithNoValidData_shouldReturnResponseWith() throws Exception {
        mockMvc.perform(post(CALCULATION_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CART_WITH_NOT_VALID_DATA_PAYLOAD))
                        .andExpect(status().isBadRequest());
    }

    private String fetchCalculationApi(String payload) throws Exception {
        return mockMvc.perform(post(CALCULATION_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void assertPrice(BigDecimal result, BigDecimal expected) {
        assertEquals(0, result.compareTo(expected));
    }
}
