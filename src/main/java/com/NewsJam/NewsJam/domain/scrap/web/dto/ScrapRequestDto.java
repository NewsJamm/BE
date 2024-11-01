package com.NewsJam.NewsJam.domain.scrap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScrapRequest {
        @Schema(description = "스크랩을 원하는 뉴스의 URL", example = "news.com")
        private String url;
        @Schema(description = "스크랩을 원하는 유저의 고유 값(로그인 아이디와는 별개)", example = "1")
        private Long memberId;
    }
}
