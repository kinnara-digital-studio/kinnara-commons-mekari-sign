package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum AttributeCategory {
    GLOBAL,
    PSRE;

    public static AttributeCategory parse(String value) throws ParseException {
        switch (value) {
            case "global":
                return GLOBAL;
            case "psre":
                return PSRE;
            default:
                throw new ParseException("Error [" + AttributeCategory.class.getName() + "] parsing [" + value + "]", 0);
        }
    }
}
