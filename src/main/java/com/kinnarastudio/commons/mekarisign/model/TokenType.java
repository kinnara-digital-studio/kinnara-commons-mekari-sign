package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum TokenType {
    BEARER;

    public static TokenType parse(String value) throws ParseException {
        switch (value) {
            case "bearer":
            case "Bearer":
                return BEARER;
            default:
                throw new ParseException("Error [" + TokenType.class.getName() + "] parsing [" + value + "]", 0);
        }
    }
}
