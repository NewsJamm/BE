package com.NewsJam.NewsJam.domain.member.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;
    @Override
    public Optional<Member> getMemberWithAuthProviderAndProviderId(AuthProvider authProvider, String providerId) {
        return memberRepository.findByAuthProviderAndProviderId(authProvider, providerId);
    }

    @Override
    public Optional<Member> getMemberWithLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
