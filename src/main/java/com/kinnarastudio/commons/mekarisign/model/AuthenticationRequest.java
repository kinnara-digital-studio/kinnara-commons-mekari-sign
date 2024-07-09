package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

public class AuthenticationRequest {
    private final String clientId;
    private final String clientSecret;
    private final GrantType grantType;
    private final String code;

    public AuthenticationRequest(String clientId, String clientSecret, GrantType grantType, String code) {
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

    public JSONObject toJson() {
        return new JSONObject() {{
            put("client_id", clientId);
            put("client_secret", clientSecret);
            put("grant_type", grantType.toString());
            put("code", code);
        }};
    }
}
