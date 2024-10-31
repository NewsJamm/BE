package com.NewsJam.NewsJam.domain.scrap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScrapRequest {
        private String url;
        private Long memberId;
    }
}
