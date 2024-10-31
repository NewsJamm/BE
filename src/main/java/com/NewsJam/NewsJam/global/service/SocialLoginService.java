package com.NewsJam.NewsJam.global.service;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.global.security.userinfo.GoogleOAuth2UserInfo;
import com.NewsJam.NewsJam.global.security.userinfo.OAuth2UserInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final RestTemplate restTemplate;

    @Value("${oauth.end-point.google}")
    private String GOOGLE_AUTH_ENDPOINT;

    public OAuth2UserInfo verifyTokenAndGetOAuth2UserInfo(AuthProvider authProvider, String accessToken){
        if(authProvider.equals(AuthProvider.GOOGLE)){
            return googleOAuth(accessToken);
        }
        else throw new AuthenticationServiceException("지원하지 않는 소셜로그인 입니다.");
    }

    private OAuth2UserInfo googleOAuth(String accessToken){
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(GOOGLE_AUTH_ENDPOINT)
                .queryParam("access_token", accessToken);

        Map<String, Object> response = restTemplate.getForObject(uriBuilder.toUriString(), Map.class);

        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(response);

        if(response == null){
            throw new AuthenticationServiceException("access token이 올바르지 않습니다.");
        }

        return userInfo;
    }


}
