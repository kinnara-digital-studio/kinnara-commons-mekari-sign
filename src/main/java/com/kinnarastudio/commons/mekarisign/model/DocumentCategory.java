package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum DocumentCategory {
    GLOBAL,
    PSRE;

    public static DocumentCategory parse(String value) throws ParseException {
        switch (value) {
            case "global":
                return GLOBAL;
            case "psre":
                return PSRE;
            default:
                throw new ParseException("Error [" + DocumentCategory.class.getName() + "] parsing [" + value + "]", 0);
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
