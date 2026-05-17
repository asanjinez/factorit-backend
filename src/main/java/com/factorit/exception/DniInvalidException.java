package com.factorit.exception;

import org.springframework.http.HttpStatus;

public class DniInvalidException extends FactoritException {

    public DniInvalidException() {
        super("DNI_INVALID", "DNI must contain exactly 8 digits", HttpStatus.BAD_REQUEST);
    }
}
