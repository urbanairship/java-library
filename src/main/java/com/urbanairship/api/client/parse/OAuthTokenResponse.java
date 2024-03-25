package com.urbanairship.api.client.parse;

public class OAuthTokenResponse {
    private String accessToken;
    private long expiresIn;
    private long tokenAcquiredAt;
    private long tokenExpiresAt;

    public OAuthTokenResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenAcquiredAt = System.currentTimeMillis();
        this.tokenExpiresAt = this.tokenAcquiredAt + (this.expiresIn * 1000);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public long getTokenAcquiredAt() {
        return tokenAcquiredAt;
    }

    public long getTokenExpiresAt() {
        return tokenExpiresAt;
    }

    public boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpiresAt;
    }
}
