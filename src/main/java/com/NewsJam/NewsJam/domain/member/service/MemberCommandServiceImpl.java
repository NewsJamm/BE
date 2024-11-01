package com.NewsJam.NewsJam.domain.member.service;

import com.NewsJam.NewsJam.domain.member.entity.Authority;
import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.Authorities;
import com.NewsJam.NewsJam.domain.member.exception.ExistLoginIdException;
import com.NewsJam.NewsJam.domain.member.exception.UserNotExistException;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.member.web.dto.InterestingKeywordsRequestDto;
import com.NewsJam.NewsJam.domain.member.web.dto.MemberRequestDto;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    @Override
    public Member join(MemberRequestDto.Request request) {
        log.info("Member request: {}", request);
        if(memberRepository.findAllByLoginId(request.getLoginId()).isEmpty()){
            log.info("Member already exists");
            throw new GeneralException(ErrorStatus._MEMBER_NOT_EXIST);
        }
        String encodedPassword = pwEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .loginId(request.getLoginId())
                .memberName(request.getName())
                .loginPw(encodedPassword)
                .scrapList(new ArrayList<>())
                .interestingKeywords(new ArrayList<>())
                .authorities(new ArrayList<>())
                .build();
        member.addAuthority(Authority.builder().type(Authorities.ROLE_MEMBER).build());
        return memberRepository.save(member);
    }

    @Override
    public void updateKeywords(InterestingKeywordsRequestDto.Request request, Member member) {
        Member getMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST));

        member.addInterestingKeywords(request.getInterestingKeywords());
        memberRepository.save(member);
    }

}
