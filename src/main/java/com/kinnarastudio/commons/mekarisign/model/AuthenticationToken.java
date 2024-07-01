package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class AuthenticationToken {
    private final String accessToken;
    private final TokenType tokenType;
    private final long expired;
    private final String refreshToken;

    public AuthenticationToken(JSONObject fromJson) throws ParseException {
        this.accessToken = fromJson.getString("access_token");;
        this.tokenType = TokenType.parse(fromJson.getString("token_type"));
        this.expired = fromJson.getLong("expires_in");
        this.refreshToken = fromJson.getString("refresh_token");;
    }

    public AuthenticationToken(String accessToken, TokenType tokenType, long expired, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expired = expired;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public long getExpired() {
        return expired;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
