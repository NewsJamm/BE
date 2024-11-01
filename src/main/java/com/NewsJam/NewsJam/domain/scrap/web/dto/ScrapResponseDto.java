package com.NewsJam.NewsJam.domain.scrap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScrapResponse{
        @Schema(description = "스크랩 성공 후 반환되는 스크랩된 뉴스의 URL", example = "successExample.com")
        String url;
        @Schema(description = "스크랩 작업을 한 유저 고유값(로그인 아이디와는 별개)", example = "1")
        Long memberId;
    }
}
