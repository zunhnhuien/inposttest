package com.eu.inpost.inposttest.service.impl;

import com.eu.inpost.inposttest.service.CartCalculatorService;
import com.eu.inpost.inposttest.service.dto.request.CartDto;
import com.eu.inpost.inposttest.service.dto.request.CartEntryDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountDto;
import com.eu.inpost.inposttest.service.dto.request.DiscountTypeDto;
import com.eu.inpost.inposttest.service.dto.response.CalculatedCartDto;
import com.eu.inpost.inposttest.service.dto.response.CalculatedCartEntryDto;
import com.eu.inpost.inposttest.service.dto.validator.CartDtoValidatorUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultCartCalculatorService implements CartCalculatorService {
    private final CartDtoValidatorUtil cartDtoValidatorUtil;

    public DefaultCartCalculatorService(CartDtoValidatorUtil cartDtoValidatorUtil) {
        this.cartDtoValidatorUtil = cartDtoValidatorUtil;
    }

    @Override
    public CalculatedCartDto calculateCart(final CartDto cartDto) {
        validateCart(cartDto);

        final Map<String, DiscountDto> appliedDiscounts = getDiscountMap(cartDto);


        final List<CalculatedCartEntryDto> calculatedCartEntries = cartDto.getProducts().stream()
                .map(e -> calculateCartEntry(e, appliedDiscounts))
                .toList();


        final BigDecimal cartBasePrice = calculatedCartEntries.stream()
                .map(CalculatedCartEntryDto::getBasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal cartDiscount = calculatedCartEntries.stream()
                .map(CalculatedCartEntryDto::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal cartFinalPrice = cartBasePrice.subtract(cartDiscount);


        return new CalculatedCartDto(cartBasePrice, cartFinalPrice, cartDiscount, calculatedCartEntries);
    }

    private Map<String, DiscountDto> getDiscountMap(final CartDto cartDto) {
        return cartDto.getAppliedDiscount().stream()
                .collect(Collectors.toMap(DiscountDto::getProductUUID, discount -> discount));
    }


    private CalculatedCartEntryDto calculateCartEntry(final CartEntryDto productEntryRequestDto,
                                                      final Map<String, DiscountDto> appliedDiscounts) {
        final BigDecimal price = productEntryRequestDto.getPrice();
        final BigDecimal qty = BigDecimal.valueOf(productEntryRequestDto.getQty());

        final BigDecimal basePrice = price.multiply(qty);
        final BigDecimal discount =
                calculateCartEntryDiscount(basePrice,
                        productEntryRequestDto.getQty(),
                        appliedDiscounts,
                        productEntryRequestDto.getUuid());
        final BigDecimal finalPrice = basePrice.subtract(discount);


        return new CalculatedCartEntryDto(productEntryRequestDto.getUuid(), basePrice, discount, finalPrice);
    }

    private BigDecimal calculateCartEntryDiscount(final BigDecimal basePrice,
                                                  final int qty,
                                                  final Map<String, DiscountDto> appliedDiscount,
                                                  String productUUID) {


        if (Objects.isNull(appliedDiscount.get(productUUID))) {
            return BigDecimal.ZERO;
        }

        final DiscountDto discount = appliedDiscount.get(productUUID);


        if (DiscountTypeDto.PERCENTAGE_BASED.equals(discount.getType()) && qty >= discount.getThreshold()) {
            return basePrice
                    .multiply(BigDecimal.valueOf(discount.getDiscountValue()))
                    .divide(new BigDecimal(100), RoundingMode.HALF_UP);
        }

        if (DiscountTypeDto.AMOUNT_BASED.equals(discount.getType()) && qty >= discount.getThreshold()) {
            return basePrice.subtract(BigDecimal.valueOf(discount.getDiscountValue()));
        }


        return BigDecimal.ZERO;


    }


    private void validateCart(CartDto cartDto) {
        cartDtoValidatorUtil.validate(cartDto);
    }

}
