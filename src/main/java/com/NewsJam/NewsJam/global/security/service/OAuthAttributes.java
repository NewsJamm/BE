package com.NewsJam.NewsJam.global.security.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.global.security.userinfo.GoogleOAuth2UserInfo;
import com.NewsJam.NewsJam.global.security.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // 고유 값 => loginId
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * LoginType에 맞는 OAuthAttributes 객체 생성
     */
    public static OAuthAttributes of(
                                     String userNameAttributeName, Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }


    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .loginId(oauth2UserInfo.getId())
                .loginPw(UUID.randomUUID().toString())
                .memberName(oauth2UserInfo.getNickname())
                .scrapList(new ArrayList<>())
                .authorities(new ArrayList<>())
                .build();
    }
}
