package com.NewsJam.NewsJam.global.security.jwt.handler;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import com.NewsJam.NewsJam.global.security.jwt.service.JwtService;
import com.NewsJam.NewsJam.global.security.service.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails)authentication.getPrincipal();
        AuthProvider authProvider = principal.getAuthProvider();
        String providerId = principal.getProviderId();
        log.info("Social Login Success :: authProvider = {} :: providerId = {}", authProvider, providerId);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                ApiResponse.onSuccess("로그인에 성공했습니다.")));
        loginSuccess(response, authProvider, providerId);
    }

    private void loginSuccess(HttpServletResponse response, AuthProvider authProvider, String providerId) {
        String accessToken = jwtService.createAccessToken(authProvider, providerId);
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(authProvider, providerId, refreshToken);
    }
}
