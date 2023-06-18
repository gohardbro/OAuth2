package com.example.oauth2.controller;

import com.example.oauth2.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    // 승인코드 요청
    @GetMapping("/oauth")
    public String auth() {
        // 인증 uri 생성
        String uri = googleAuthService.getAuthUri();
        System.out.println("1.승인코드 요청 완료! auth uri: " + uri);

        // 생성된 uri로 리다이렉트
        return "redirect:" + uri;
    }

    // 승인코드 응답받아 access token 으로 교환
    @GetMapping("/callback")
    public String handleCallback(@RequestParam("code") String authCode) {

        System.out.println("2.승인코드 발급 완료! auth code: " + authCode);

        // access token 으로 교환
        String res = googleAuthService.exchangeToken(authCode);
        JSONObject jsonObject = new JSONObject(res);
        String accessToken = jsonObject.get("access_token").toString();

        // 유튭 api 테스트
        googleAuthService.youtubeApiTest(accessToken);

        return "/main";
    }



}
