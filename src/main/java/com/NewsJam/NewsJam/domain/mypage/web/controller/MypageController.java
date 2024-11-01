package com.NewsJam.NewsJam.domain.mypage.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.mypage.service.MypageService;
import com.NewsJam.NewsJam.domain.mypage.web.dto.ScrapNewsListResponseDto;
import com.NewsJam.NewsJam.global.annotation.LoginMember;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/getScrapList")
    public ApiResponse<ScrapNewsListResponseDto.ScrapNewsList> getScrapList(@LoginMember Member member) {
        log.info(":: MypageController Called ::");
        log.info("memberId: {}", member.getId());
        ScrapNewsListResponseDto.ScrapNewsList response = mypageService.scrapNewsList(member.getId());

        return ApiResponse.onSuccess(response);
    }
}
