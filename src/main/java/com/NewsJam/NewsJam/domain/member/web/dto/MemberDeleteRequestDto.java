package com.NewsJam.NewsJam.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDeleteRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberDeleteRequest{

        @Schema(description = "탈퇴를 원하는 유저의 고유값(로그인아이디와는 별개", example = "1")
        private Long memberId;
    }
}
