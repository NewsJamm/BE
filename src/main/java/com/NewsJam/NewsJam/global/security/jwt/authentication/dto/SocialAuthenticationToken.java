package com.NewsJam.NewsJam.global.security.jwt.authentication.dto;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public SocialAuthenticationToken(AuthProvider authProvider, String accessToken) {
        super(authProvider, accessToken);
    }
}
