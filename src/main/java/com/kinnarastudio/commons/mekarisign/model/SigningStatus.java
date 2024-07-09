package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum SigningStatus {
    IN_PROGRESS,
    COMPLETED,
    VOIDED,
    DECLINED;

    public static SigningStatus parse(String value) throws ParseException {
        switch (value) {
            case "in_progress":
                return IN_PROGRESS;
            case "completed":
                return COMPLETED;
            case "voided":
                return VOIDED;
            case "declined":
                return DECLINED;
            default:
                throw new ParseException("Error [" + SigningStatus.class.getName() + "] parsing [" + value + "]", 0);
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
