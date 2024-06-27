package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum AnnotationType {
    SIGNATURE,
    MATERAI,
    INITIAL;

    public static AnnotationType parse(String value) throws ParseException {
        switch (value) {
            case "signature":
                return SIGNATURE;
            case "materai":
                return MATERAI;
            case "initial":
                return INITIAL;
            default:
                throw new ParseException("Error parsing value [" + value + "]", 0);
        }
    }
}
