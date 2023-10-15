package com.nineleaps.authentication.jwt.dto;

public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String errorMessage;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String refreshToken, String errorMessage) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errorMessage = errorMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}



