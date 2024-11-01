package com.NewsJam.NewsJam.domain.mypage.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewScrapListRequestDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewScrapDto {
        @Schema(description = "사용자의 스크랩 정보를 가져오기 위한 유저 고유값(로그인 아이디와는 별개)", example = "1")
        private Long memberId;
    }
}
