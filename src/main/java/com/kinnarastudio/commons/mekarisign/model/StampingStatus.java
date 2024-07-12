package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum StampingStatus {
    IN_PROGRESS, NONE, PENDING, FAILED, SUCCESS;

    public static StampingStatus parse(String value) throws ParseException {
        switch (value) {
            case "none":
                return NONE;
            case "in_progress":
                return IN_PROGRESS;
            case "pending":
                return PENDING;
            case "failed":
                return FAILED;
            case "success":
                return SUCCESS;
            case "null":
                return null;
            default:
                throw new ParseException("Error [" + StampingStatus.class.getName() + "] parsing [" + value + "]", 0);
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
