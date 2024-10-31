package com.NewsJam.NewsJam.global.security.jwt.handler;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws
            IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(400);
        response.getWriter().write(objectMapper.writeValueAsString(
                ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(),ErrorStatus._BAD_REQUEST.getMessage(), "로그인에 실패했습니다.")));
        log.info("social Login fail :: error = {}", exception.getMessage());
    }
}
