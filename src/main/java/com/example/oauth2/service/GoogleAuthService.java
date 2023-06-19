package com.example.oauth2.service;

import com.example.oauth2.model.dto.GoogleOauthInfo;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleOauthInfo googleOauthInfo;
    private final StringEncryptor encryptor;
    // 인증 uri 생성
    public String getAuthUri() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme("https")
                .host("accounts.google.com")
                .path("/o/oauth2/v2/auth")
                .queryParam("client_id", encryptor.decrypt(googleOauthInfo.getClientId()))
                .queryParam("redirect_uri", googleOauthInfo.getRedirectUri())
                .queryParam("response_type", googleOauthInfo.getResponseType())
                .queryParam("scope", googleOauthInfo.getScope())
                .queryParam("access_type", googleOauthInfo.getAccessType());

        return uriBuilder.toUriString();
    }

    // 승인코드로 엑세스토큰 받기
    public String exchangeToken(String authCode) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://oauth2.googleapis.com/token";

        // 요청 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 전송 데이터 설정
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", authCode);
        requestBody.add("client_id", encryptor.decrypt(googleOauthInfo.getClientId()));
        requestBody.add("client_secret", encryptor.decrypt(googleOauthInfo.getClientSecret()));
        requestBody.add("redirect_uri", googleOauthInfo.getRedirectUri());
        requestBody.add("grant_type", googleOauthInfo.getGrantType());

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, httpHeaders),
                String.class);

        // 응답 결과 출력
        String res = response.getBody();
        System.out.println("3.accessToken 발급 완료! Response: " + res);

        return res;
    }

    // 유튭 api 호출 테스트
    public void youtubeApiTest(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.googleapis.com/youtube/v3/channels?part=snippet&mine=true";

        //요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // 요청 발송
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        // 응답 결과 출력
        String res = response.getBody();
        System.out.println("4.유튭 api 호출 완료! youtube api response: " + res);
    }

}
