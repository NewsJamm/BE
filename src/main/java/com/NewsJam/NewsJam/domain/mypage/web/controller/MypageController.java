package com.NewsJam.NewsJam.domain.mypage.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.mypage.service.MypageService;
import com.NewsJam.NewsJam.domain.mypage.web.dto.ScrapNewsListResponseDto;
import com.NewsJam.NewsJam.global.annotation.LoginMember;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "마이페이지 API", description = "마이페이지 관련 API")
public class MypageController {
    private final MypageService mypageService;

    @Operation(summary = "스크랩된 뉴스 리스트 조회", description = "회원에 따라 스크랩한 뉴스 리스트를 반환")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 리스트 조회 성공",
                    content = @Content(schema = @Schema(implementation = ScrapNewsListResponseDto.ScrapNewsList.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @GetMapping("/getScrapList")
    public ApiResponse<ScrapNewsListResponseDto.ScrapNewsList> getScrapList(

            @Parameter(description = "스크랩 뉴스 리스트 조회 요청 정보", required = true)
            @LoginMember Member member) {

        log.info(":: MypageController Called ::");
        log.info("memberId: {}", member.getId());

        ScrapNewsListResponseDto.ScrapNewsList response = mypageService.scrapNewsList(member.getId());
        return ApiResponse.onSuccess(response);
    }
}