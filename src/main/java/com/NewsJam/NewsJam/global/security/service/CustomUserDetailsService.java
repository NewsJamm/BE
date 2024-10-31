package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.global.security.userinfo.OAuth2UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserByOAuth2UserInfo(OAuth2UserInfo userInfo, AuthProvider authProvider);
}
