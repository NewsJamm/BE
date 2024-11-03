package com.NewsJam.NewsJam.domain.main.viewcount.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewCountIncreaseResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewCountIncreaseResponse {
        @Schema(description = "뉴스 고유번호", example = "5")
        private Long id;
        @Schema(description = "뉴스 url 주소", example = "www.myNews.com")
        private String url;
        @Schema(description = "뉴스 조회 수", example = "35")
        private Long viewCnt;
    }
}
