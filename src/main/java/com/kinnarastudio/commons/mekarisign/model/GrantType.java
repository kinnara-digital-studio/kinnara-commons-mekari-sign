package com.kinnarastudio.commons.mekarisign.model;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    GrantType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
