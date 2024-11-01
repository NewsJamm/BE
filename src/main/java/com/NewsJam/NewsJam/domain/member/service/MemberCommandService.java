package com.NewsJam.NewsJam.domain.member.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.web.dto.InterestingKeywordsRequestDto;
import com.NewsJam.NewsJam.domain.member.web.dto.MemberRequestDto;

public interface MemberCommandService {
    Member join(MemberRequestDto.MemberRequest memberRequest);
    void updateKeywords(InterestingKeywordsRequestDto.InterestingKeywordRequest interestingKeywordRequest, Long memberId);
    void memberDelete(Long memberId);
}
