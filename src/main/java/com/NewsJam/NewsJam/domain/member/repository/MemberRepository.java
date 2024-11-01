package com.NewsJam.NewsJam.domain.member.repository;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String principal);

    Optional<Member> findAllByLoginId(String loginId);

    Optional<Member> findById(Long id);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByAuthProviderAndProviderId(AuthProvider authProvider, String providerId);
}
