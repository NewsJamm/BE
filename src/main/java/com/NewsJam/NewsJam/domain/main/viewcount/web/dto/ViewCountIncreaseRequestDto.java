package com.NewsJam.NewsJam.domain.main.viewcount.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewCountIncreaseRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewCountIncreaseRequest {
        @Schema(description = "조회하는 뉴스 url 주소", example = "www.news.com")
        String url;
    }
}
