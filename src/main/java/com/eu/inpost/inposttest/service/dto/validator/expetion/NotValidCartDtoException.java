package com.eu.inpost.inposttest.service.dto.validator.expetion;

public class NotValidCartDtoException extends RuntimeException {

    public NotValidCartDtoException() {
        super();
    }


    public NotValidCartDtoException(String s) {
        super(s);
    }
}
