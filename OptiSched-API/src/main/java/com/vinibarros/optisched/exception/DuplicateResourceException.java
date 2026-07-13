package com.vinibarros.optisched.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message){
        super(message);
    }

    public DuplicateResourceException(String entityName, String fieldName, Object fieldValue) {
        super(entityName + " already exists with " + fieldName + ": " + fieldValue);
    }
}
