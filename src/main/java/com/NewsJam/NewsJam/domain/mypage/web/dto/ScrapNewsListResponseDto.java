package com.NewsJam.NewsJam.domain.mypage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

public class ScrapNewsListResponseDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapNewsList {
        public List<newsList> scrapList;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class newsList{
        public String title;
        public String pubDate;
        public String url;
    }
}
