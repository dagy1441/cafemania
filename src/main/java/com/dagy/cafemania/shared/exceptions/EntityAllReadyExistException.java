package com.dagy.cafemania.shared.exceptions;

public class EntityAllReadyExistException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public EntityAllReadyExistException(String resourceName, String fieldName, String fieldValue) {

        super(String.format("La ressource %s avec l'attribut %s : '%s'  existe dèjà", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
