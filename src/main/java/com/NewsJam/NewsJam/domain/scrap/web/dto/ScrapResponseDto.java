package com.NewsJam.NewsJam.domain.scrap.web.dto;

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
        String url;
        Long memberId;
    }
}
