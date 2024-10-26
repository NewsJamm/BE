package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.global.security.dto.OAuthAttributes;
import com.NewsJam.NewsJam.global.security.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 공급자 이름 (예: google, naver, etc.)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 시 사용하는 키 값 (Google은 "sub")
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2User에서 사용자 정보 가져오기
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 사용자 정보 저장 또는 업데이트
        Member member = saveOrUpdate(attributes);

        // 세션에 사용자 정보 저장 (직렬화된 객체로 저장)
        httpSession.setAttribute("user", new SessionUser(member));

        return attributes.toOAuth2User(member.getRole().getGrantedAuthorities());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Optional<Member> memberOptional = memberRepository.findByEmail(attributes.getEmail());

        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            member.update(attributes.getName(), attributes.getPicture());
        } else {
            member = attributes.toEntity();
        }

        return memberRepository.save(member);
    }
}
