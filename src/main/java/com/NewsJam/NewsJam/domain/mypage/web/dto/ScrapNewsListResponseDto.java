package com.NewsJam.NewsJam.domain.mypage.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

public class ScrapNewsListResponseDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapNewsList {
        @Schema(description = "스크랩 정보들이 담긴 리스트", example = "scrapList=[" +
                "newsList={" +
                    "title=\"제목1\"," +
                    "pubDate=\"2024-11-01\"," +
                    "url=\"example.com\"" +
                    "}" +
                "]")
        public List<newsList> scrapList;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class newsList{
        @Schema(description = "스크랩 된 뉴스 제목", example = "\"제목1\"")
        public String title;
        @Schema(description = "스크랩 된 뉴스의 발행일", example = "\"2024-10-30\"")
        public String pubDate;
        @Schema(description = "스크랩 된 뉴스 URL", example = "\"news.com\"")
        public String url;
    }
}
