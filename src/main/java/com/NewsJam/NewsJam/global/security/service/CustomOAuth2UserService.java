package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.entity.Authority;
import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.Authorities;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger log = LogManager.getLogger(CustomOAuth2UserService.class);

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 부모 클래스로 부터 OAuth2User를 받음
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 인증 서버 ID
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 인증 서버에서 제공하는 고유 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 유저 정보

        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        Member createdUser = getUser(extractAttributes); // getUser() 메소드로 User 객체 생성 후 반환

        log.info("createdUser ={}", createdUser);

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                createdUser.getAuthorities()
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getType().toString()))
                        .collect(
                                Collectors.toList()),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getLoginId(),
                createdUser.getAuthorities().isEmpty() ? null : createdUser.getAuthorities().get(0).getType()
        );
    }

    /**
     * 인증 서버로 부터 받은 고유 키 값으로 유저 검색
     * -> 존재하지 않는다면 새로운 유저 생성
     */
    private Member getUser(OAuthAttributes attributes) {
        Member findUser = memberRepository.findByLoginId(
                attributes.getOauth2UserInfo().getId()).orElse(null);

        log.info("findUser = {}", findUser);
        if (findUser == null) {
            return saveUser(attributes);
        }
        return findUser;
    }

    /**
     * OAUTH2 최초 로그인 유저 생성
     */
    private Member saveUser(OAuthAttributes attributes) {
        Member createdUser = attributes.toEntity(attributes.getOauth2UserInfo());
        createdUser.addAuthority(Authority.builder().type(Authorities.ROLE_MEMBER).build());
        return memberRepository.save(createdUser);
    }
}