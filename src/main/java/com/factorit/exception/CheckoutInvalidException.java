package com.factorit.exception;

import org.springframework.http.HttpStatus;

public class CheckoutInvalidException extends FactoritException {

    public CheckoutInvalidException(String message) {
        super("CHECKOUT_INVALID", message, HttpStatus.BAD_REQUEST);
    }
}
