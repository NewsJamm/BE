package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.entity.Authority;
import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.domain.member.enums.Authorities;
import com.NewsJam.NewsJam.domain.member.repository.AuthorityRepository;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.global.security.userinfo.OAuth2UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements CustomUserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByOAuth2UserInfo(OAuth2UserInfo userInfo, AuthProvider authProvider) {
        Member result;
        Optional<Member> memberOptional = memberRepository.findByAuthProviderAndProviderId(
                authProvider, userInfo.getId());

        if(!memberOptional.isPresent()){
            Member member = Member.builder()
                    .memberName(userInfo.getNickname())
                    .authProvider(authProvider)
                    .providerId(userInfo.getId())
                    .authorities(new ArrayList<>())
                    .build();

            Authority authority = Authority.builder()
                    .type(Authorities.ROLE_MEMBER)
                    .build();
            
            member.addAuthority(authority);
            
            result = memberRepository.save(member);
        }
        else {
            result = memberOptional.get();
        }

        List<SimpleGrantedAuthority> authorityList = authorityRepository.findByMemberId(result.getId()).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getType().toString())).collect(Collectors.toList());

        return new CustomUserDetails(result, authorityList);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        List<SimpleGrantedAuthority> authorityList = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getType().toString()))
                .collect(Collectors.toList());

        return new CustomUserDetails(member, authorityList);
    }
}
