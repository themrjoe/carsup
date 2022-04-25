package com.el.opu.carsup.api;

public class ApiException extends RuntimeException {

    ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
