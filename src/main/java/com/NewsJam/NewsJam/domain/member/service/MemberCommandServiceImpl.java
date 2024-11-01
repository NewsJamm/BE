package com.NewsJam.NewsJam.domain.member.service;

import com.NewsJam.NewsJam.domain.member.entity.Authority;
import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.enums.Authorities;
import com.NewsJam.NewsJam.domain.member.exception.UserNotExistException;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.member.web.dto.InterestingKeywordsRequestDto;
import com.NewsJam.NewsJam.domain.member.web.dto.MemberRequestDto;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    @Override
    public Member join(MemberRequestDto.MemberRequest memberRequest) {
        log.info("Member request: {}", memberRequest);
        if (memberRepository.findByLoginId(memberRequest.getLoginId()).isPresent()) {
            log.info("Member already exists");
            throw new GeneralException(ErrorStatus._EXIST_LOGINID);
        }
        String encodedPassword = pwEncoder.encode(memberRequest.getName());

        Member member = Member.builder()
                .loginId(memberRequest.getLoginId())
//                .memberName(memberRequest.getName())
                .loginPw(encodedPassword)
                .scrapList(new ArrayList<>())
                .interestingKeywords(new ArrayList<>())
                .authorities(new ArrayList<>())
                .build();
        member.addAuthority(Authority.builder().type(Authorities.ROLE_MEMBER).build());
        return memberRepository.save(member);
    }

    @Override
    public void updateKeywords(InterestingKeywordsRequestDto.InterestingKeywordRequest interestingKeywordRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST));

        member.addInterestingKeywords(interestingKeywordRequest.getInterestingKeywords());
        memberRepository.save(member);
    }

    @Override
    public void memberDelete(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isEmpty()) {
            log.info("Member not found");
            throw new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST);
        }
        Member found = findMember.get();
        memberRepository.delete(found);
    }

}
