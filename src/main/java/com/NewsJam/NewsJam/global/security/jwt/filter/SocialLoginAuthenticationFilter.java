package com.NewsJam.NewsJam.global.security.jwt.filter;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.global.security.jwt.authentication.dto.SocialAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

@Slf4j
/**
 * JWT 로그인 POST 요청 왔을 때 인증 필터
 */
public class SocialLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_LOGIN_REQUEST_URL = "/api/auth/social-login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String AUTH_PROVIDER_KEY = "auth_provider";
    private static final String ACCESS_TOKEN_KEY = "access_token";

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    private final ObjectMapper objectMapper;

    public SocialLoginAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // 기존 formlogin (/login) 형태를 변경
        this.objectMapper = objectMapper;
    }

    /**
     * 소셜 로그인 인증 과정
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException,
            IOException,
            ServletException {
        // 지원하는 ContentType이 아닌 경우
        if (request.getContentType() == null || !request.getContentType().startsWith(CONTENT_TYPE)) {
            throw new AuthenticationServiceException(
                    "Authentication Content-Type not supported: " + request.getContentType());
        }

        // request body에서 로그인 ID와 비밀번호 추출
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);
        AuthProvider authProvider = AuthProvider.fromString(usernamePasswordMap.get(AUTH_PROVIDER_KEY))
                .orElseThrow(() -> new AuthenticationServiceException(
                        "지원하지 않는 소셜 로그인 항목입니다."));
        String accessToken = usernamePasswordMap.get(ACCESS_TOKEN_KEY);

        //principal 과 credentials 전달
        SocialAuthenticationToken authToken = new SocialAuthenticationToken(authProvider, accessToken);

        return this.getAuthenticationManager().authenticate(authToken);
    }

}
