package com.NewsJam.NewsJam.global.security.jwt.filter;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.domain.member.repository.AuthorityRepository;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.member.service.MemberQueryService;
import com.NewsJam.NewsJam.global.security.jwt.service.JwtService;
import com.NewsJam.NewsJam.global.security.service.CustomUserDetails;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
/**
 * JWT Authentication 필터
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final AuthorityRepository authorityRepository;

    /**
     * 로그인 요청 시 JWT 검증 X
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals(LocalLoginAuthenticationFilter.DEFAULT_LOGIN_REQUEST_URL) || request.getServletPath().equals(SocialLoginAuthenticationFilter.DEFAULT_LOGIN_REQUEST_URL);
    }

    /**
     * 	JWT 검증 후
     * 	요청에 Refresh Token 존재 -> Refresh Token 검증 후 Access Token, Refresh Token 생성
     * 	요청에 Refresh Token 존재 X -> Access Token 검증
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    /**
     * Refresh Token이 유효한 지 검증 후 Access Token 재발급
     */
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    // AccessToken, RefreshToken response에 전달
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getAuthProvider(), member.getProviderId()),
                            reIssuedRefreshToken);
                });
    }

    /**
     * Refresh Token 재발급
     */
    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        // 새로운 Refresh Token으로 업데이트
        member.changeRefreshToken(reIssuedRefreshToken);
        return reIssuedRefreshToken;
    }

    /**
     * Access Token 검증 후 인증
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {

        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> {
                    Optional<AuthProvider> authProvider = jwtService.extractProviderType(accessToken);
                    Optional<String> providerId = jwtService.extractProviderId(accessToken);
                    Optional<String> loginId = jwtService.extractLoginId(accessToken);
                    if(authProvider.isPresent() && providerId.isPresent()){
                        memberQueryService.getMemberWithAuthProviderAndProviderId(authProvider.get(), providerId.get())
                                .ifPresent(this::saveAuthentication);
                    }
                    if(loginId.isPresent()){
                        memberQueryService.getMemberWithLoginId(loginId.get())
                                .ifPresent(this::saveAuthentication);
                    }
                });

        filterChain.doFilter(request, response);
    }

    /**
     * 검증된 토큰이면 인증
     */
    public void saveAuthentication(Member myMember) {
        List<SimpleGrantedAuthority> authorityList = authorityRepository.findByMemberId(myMember.getId()).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getType().toString()))
                .collect(Collectors.toList());


        CustomUserDetails userDetails = new CustomUserDetails(myMember, authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authorityList);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
