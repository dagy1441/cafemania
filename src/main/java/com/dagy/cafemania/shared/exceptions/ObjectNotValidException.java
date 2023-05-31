package com.dagy.cafemania.shared.exceptions;

import lombok.Data;

import java.util.Set;

@Data
public class ObjectNotValidException extends RuntimeException {

    private final Set<Object> fields;
    private final Set<String> errorMessages;
}
