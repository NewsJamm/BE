package com.NewsJam.NewsJam.domain.member.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.service.MemberCommandService;
import com.NewsJam.NewsJam.domain.member.web.dto.InterestingKeywordsRequestDto;
import com.NewsJam.NewsJam.domain.member.web.dto.MemberRequestDto;
import com.NewsJam.NewsJam.global.annotation.LoginMember;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원 관리 API", description = "회원 가입 및 키워드 관리 API")
public class MemberController {
    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = Member.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @PostMapping("/api/signin")
    public ApiResponse<?> signIn(@Valid @RequestBody MemberRequestDto.MemberRequest memberRequest) {
        Member newMember = memberCommandService.join(memberRequest);

        return ApiResponse.onSuccess(newMember);
    }

    @Operation(summary = "관심 키워드 업데이트", description = "해당 회원의 관심 키워드 업데이트")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "키워드 저장 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @PostMapping("/api/interesting-keywords")
    public ApiResponse<?> interestingKeywords(@Valid @RequestBody InterestingKeywordsRequestDto.InterestingKeywordRequest interestingKeywordRequest, @LoginMember Member member) {
        memberCommandService.updateKeywords(interestingKeywordRequest, member.getId());

        return ApiResponse.onSuccess("키워드 저장에 성공하였습니다.");
    }

    @Operation(summary = "회원 탈퇴 메서드", description = "회원 탈퇴 기능")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 탈퇴에 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @DeleteMapping("/api/memberDelete")
    public ApiResponse<?> userDelete(@LoginMember Member member) {
        log.info("request memberId = {}",member.getId());

        memberCommandService.memberDelete(member.getId());

        return ApiResponse.onSuccess("회원 탈퇴에 성공하였습니다.");
    }

}
