package com.example.oauth2.model.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleOauthInfo {
    @Value("${secrets.googleOAuth.clientId}")
    private String clientId;

    @Value("${secrets.googleOAuth.clientSecret}")
    private String clientSecret;

    @Value("authorization_code")
    private String grantType;

    @Value("http://localhost:8080/callback")
    private String redirectUri;

    @Value("code")
    private String responseType;

    @Value("https://www.googleapis.com/auth/youtube")
    private String scope;

    @Value("offline")
    private String accessType;
}
