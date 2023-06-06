package com.alphanove.AN_journey.exception;

public class SchemaException extends RuntimeException {
    private String errorCode;

    public SchemaException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
