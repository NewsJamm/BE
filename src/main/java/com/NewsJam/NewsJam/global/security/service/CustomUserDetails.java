package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Member member;
    private Collection<? extends GrantedAuthority> authorityList;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorityList) {
        this.member = member;
        this.authorityList = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return member.getLoginPw();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    public AuthProvider getAuthProvider() {
        return member.getAuthProvider();
    }

    public String getProviderId() {
        return member.getProviderId();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
