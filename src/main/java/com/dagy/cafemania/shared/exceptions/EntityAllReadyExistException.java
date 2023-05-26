package com.dagy.cafemania.shared.exceptions;

public class EntityAllReadyExistException extends RuntimeException {
    public EntityAllReadyExistException(String message) {
        super(message);
    }

    public EntityAllReadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
