package com.kinnarastudio.commons.mekarisign.exception;

public class InvalidTokenException extends Exception {
    final private int errorCode;

    public InvalidTokenException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
