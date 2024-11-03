package com.NewsJam.NewsJam.domain.search.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SearchResultResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchResult {
        @Schema(description = "검색 결과 FoundNews 객체를 가지고 있는 리스트")
        List<FoundNews> foundNews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FoundNews {
        @Schema(description = "검색 결과로 나온 뉴스의 제목", example = "title1")
        String title;
        @Schema(description = "검색 결과로 나온 뉴스의 발행일", example = "2024-10-31")
        String pubDate;
        @Schema(description = "검색 결과로 나온 뉴스의 URL", example = "example.com")
        String url;
    }
}
