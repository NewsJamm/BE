package com.NewsJam.NewsJam.domain.member.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> getMemberWithAuthProviderAndProviderId(AuthProvider authProvider, String providerId);
    Optional<Member> getMemberWithLoginId(String loginId);
}
