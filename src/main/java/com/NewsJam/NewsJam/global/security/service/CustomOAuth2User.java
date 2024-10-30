package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.enums.Authorities;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@ToString
public class CustomOAuth2User extends DefaultOAuth2User{
    private final String loginId;
    private final Authorities authority;
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String loginId, Authorities authority) {
        super(authorities, attributes, nameAttributeKey);
        this.loginId = loginId;
        this.authority = authority;
    }
}
