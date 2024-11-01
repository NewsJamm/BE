package com.NewsJam.NewsJam.global.security.config;

import com.NewsJam.NewsJam.domain.member.repository.AuthorityRepository;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.member.service.MemberQueryService;
import com.NewsJam.NewsJam.global.security.jwt.authentication.SocialAuthenticationProvider;
import com.NewsJam.NewsJam.global.security.jwt.filter.SocialLoginAuthenticationFilter;
import com.NewsJam.NewsJam.global.security.jwt.filter.LocalLoginAuthenticationFilter;
import com.NewsJam.NewsJam.global.security.jwt.filter.JwtAuthenticationFilter;
import com.NewsJam.NewsJam.global.security.jwt.handler.JwtLoginFailureHandler;
import com.NewsJam.NewsJam.global.security.jwt.handler.JwtLoginSuccessHandler;
import com.NewsJam.NewsJam.global.security.jwt.handler.OAuthLoginFailureHandler;
import com.NewsJam.NewsJam.global.security.jwt.handler.OAuthLoginSuccessHandler;
import com.NewsJam.NewsJam.global.security.jwt.service.JwtService;
import com.NewsJam.NewsJam.global.security.service.LoginUserDetailsService;
import com.NewsJam.NewsJam.global.service.SocialLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final LoginUserDetailsService loginUserDetailsService;
    private final JwtService jwtService;
    private final MemberQueryService memberQueryService;
    private final AuthorityRepository authorityRepository;
    private final ObjectMapper objectMapper;
    private final JwtLoginFailureHandler jwtLoginFailureHandler;
    private final JwtLoginSuccessHandler jwtLoginSuccessHandler;
    private final OAuthLoginFailureHandler oAuthLoginFailureHandler;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final SocialLoginService socialLoginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                );

        http.addFilterAfter(socialLoginAuthenticationFilter(), LogoutFilter.class);
        http.addFilterAfter(localLoginAuthenticationFilter(), SocialLoginAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), SocialLoginAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider localLoginProvider = new DaoAuthenticationProvider();
        localLoginProvider.setPasswordEncoder(passwordEncoder());
        localLoginProvider.setUserDetailsService(loginUserDetailsService);

        SocialAuthenticationProvider socialLoginProvider = new SocialAuthenticationProvider(socialLoginService,
                loginUserDetailsService);
        return new ProviderManager(Arrays.asList(localLoginProvider, socialLoginProvider));
    }

    @Bean
    public LocalLoginAuthenticationFilter localLoginAuthenticationFilter() {
        LocalLoginAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new LocalLoginAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(jwtLoginSuccessHandler);
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(jwtLoginFailureHandler);
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    SocialLoginAuthenticationFilter socialLoginAuthenticationFilter(){
        SocialLoginAuthenticationFilter socialLoginAuthenticationFilter
                = new SocialLoginAuthenticationFilter(objectMapper);
        socialLoginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        socialLoginAuthenticationFilter.setAuthenticationSuccessHandler(oAuthLoginSuccessHandler);
        socialLoginAuthenticationFilter.setAuthenticationFailureHandler(oAuthLoginFailureHandler);
        return socialLoginAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService,
                memberRepository, memberQueryService, authorityRepository);
        return jwtAuthenticationFilter;
    }

}
