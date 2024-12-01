package com.eu.inpost.inposttest.service.dto.validator;

public interface DtoValidator<V> {
    void validate(V v);
}