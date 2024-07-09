package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class RefreshTokenRequest {
    private final String clientId;
    private final String clientSecret;
    private final GrantType grantType;
    private final String refreshToken;

    public RefreshTokenRequest(String clientId, String clientSecret, GrantType grantType, String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public JSONObject toJson() {
        return new JSONObject() {{
            put("client_id", clientId);
            put("client_secret", clientSecret);
            put("grant_type", grantType.toString());
            put("refresh_token", refreshToken);
        }};
    }
}
