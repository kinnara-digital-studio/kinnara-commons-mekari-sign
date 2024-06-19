package com.kinnarastudio.commons.mekarisign.exception;

public class MissingParameterException extends Exception{
    private final String parameterName;

    public MissingParameterException(String parameterName) {
        super("Parameter [" + parameterName + "] is missing");
        this.parameterName = parameterName;
    }
}
