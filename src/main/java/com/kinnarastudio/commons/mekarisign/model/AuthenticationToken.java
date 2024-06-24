package com.kinnarastudio.commons.mekarisign.model;

public class AuthenticationToken {
    private final String accessToken;
    private final TokenType tokenType;
    private final long expired;
    private final String refreshToken;

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
