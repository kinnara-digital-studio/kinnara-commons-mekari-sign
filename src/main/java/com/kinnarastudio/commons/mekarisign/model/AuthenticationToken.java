package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class AuthenticationToken {
    private final String accessToken;
    private final TokenType tokenType;
    private final long expired;
    private final String refreshToken;
    private final ServerType serverType;

    public AuthenticationToken(JSONObject fromJson, ServerType serverType) throws ParseException {
        this.accessToken = fromJson.getString("access_token");
        this.tokenType = TokenType.parse(fromJson.getString("token_type"));
        this.expired = fromJson.getLong("expires_in");
        this.refreshToken = fromJson.getString("refresh_token");
        this.serverType = serverType;
    }

    public AuthenticationToken(String accessToken, TokenType tokenType, long expired, String refreshToken, ServerType serverType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expired = expired;
        this.refreshToken = refreshToken;
        this.serverType = serverType;
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

    public ServerType getServerType()
    {
        return serverType;
    }
}
