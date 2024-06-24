package com.kinnarastudio.commons.mekarisign.model;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code");

    private final String value;

    GrantType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
