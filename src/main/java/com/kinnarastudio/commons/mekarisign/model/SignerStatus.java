package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

public enum SignerStatus {
    YOUR_TURN,
    AWAITING;

    public static SignerStatus parse(String value) throws ParseException {
        switch (value) {
            case "your_turn":
                return YOUR_TURN;
            case "awaiting":
                return AWAITING;
            default:
                throw new ParseException("Error [" + SignerStatus.class.getName() + "] parsing [" + value + "]", 0);
        }
    }
}
