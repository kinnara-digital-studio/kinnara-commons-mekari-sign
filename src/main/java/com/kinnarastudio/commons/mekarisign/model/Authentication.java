package com.kinnarastudio.commons.mekarisign.model;

public class Authentication {
    private final String clientId;
    private final String clientSecret;
    private final GrantType grantType;
    private final String code;

    public Authentication(String clientId, String clientSecret, GrantType grantType, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public String getCode() {
        return code;
    }
}
