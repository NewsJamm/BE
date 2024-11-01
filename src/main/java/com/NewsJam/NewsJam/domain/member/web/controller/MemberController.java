package com.NewsJam.NewsJam.domain.member.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.service.MemberCommandService;
import com.NewsJam.NewsJam.domain.member.web.dto.InterestingKeywordsRequestDto;
import com.NewsJam.NewsJam.domain.member.web.dto.MemberRequestDto;
import com.NewsJam.NewsJam.global.annotation.LoginMember;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberCommandService memberCommandService;

    @PostMapping("/api/signin")
    public ApiResponse<?> signIn(@Valid @RequestBody MemberRequestDto.Request request) {
        Member newMember = memberCommandService.join(request);

        return ApiResponse.onSuccess(newMember);
    }

    @PostMapping("/api/interesting-keywords")
    public ApiResponse<?> interestingKeywords(@Valid @RequestBody InterestingKeywordsRequestDto.Request request, @LoginMember Member member) {
        memberCommandService.updateKeywords(request, member);

        return ApiResponse.onSuccess("키워드 저장에 성공하였습니다.");
    }


}
