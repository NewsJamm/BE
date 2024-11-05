package com.NewsJam.NewsJam.domain.news.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NewsAPIResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewsData {
        String Description;
        String title;
        String pubDate;
        String originalLink;
    }
}
