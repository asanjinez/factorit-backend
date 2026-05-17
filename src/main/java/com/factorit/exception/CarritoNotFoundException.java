package com.factorit.exception;

import org.springframework.http.HttpStatus;

public class CarritoNotFoundException extends FactoritException {

    public CarritoNotFoundException() {
        super("CARRITO_NOT_FOUND", "Cart not found", HttpStatus.NOT_FOUND);
    }
}
