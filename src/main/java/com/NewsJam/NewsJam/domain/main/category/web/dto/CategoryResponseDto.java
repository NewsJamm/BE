package com.NewsJam.NewsJam.domain.main.category.web.dto;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CategoryResponseDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class response{
        NewsCategory category;
        List<newsList> newsList;
        private int size;
        private int page;
        private boolean last;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class newsList{
        String url;
        String title;
        String puhDate;
        Long viewCnt;
    }
}
