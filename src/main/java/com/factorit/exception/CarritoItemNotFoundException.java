package com.factorit.exception;

import org.springframework.http.HttpStatus;

public class CarritoItemNotFoundException extends FactoritException {

    public CarritoItemNotFoundException() {
        super("CARRITO_ITEM_NOT_FOUND", "Cart item not found", HttpStatus.NOT_FOUND);
    }
}
