package com.NewsJam.NewsJam.global.security.jwt.authentication;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.global.security.jwt.authentication.dto.SocialAuthenticationToken;
import com.NewsJam.NewsJam.global.security.service.LoginUserDetailsService;
import com.NewsJam.NewsJam.global.security.userinfo.OAuth2UserInfo;
import com.NewsJam.NewsJam.global.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class SocialAuthenticationProvider implements AuthenticationProvider {
    private final SocialLoginService socialLoginService;
    private final LoginUserDetailsService loginUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthProvider authProvider = (AuthProvider) authentication.getPrincipal();
        String accessToken = (String) authentication.getCredentials();

        OAuth2UserInfo oAuth2UserInfo = socialLoginService.verifyTokenAndGetOAuth2UserInfo(authProvider, accessToken);
        UserDetails userDetails = loginUserDetailsService.loadUserByOAuth2UserInfo(oAuth2UserInfo, authProvider);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
