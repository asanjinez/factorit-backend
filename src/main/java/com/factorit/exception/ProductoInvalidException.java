package com.factorit.exception;

import org.springframework.http.HttpStatus;

public class ProductoInvalidException extends FactoritException {

    public ProductoInvalidException(String message) {
        super("PRODUCTO_INVALID", message, HttpStatus.BAD_REQUEST);
    }
}
