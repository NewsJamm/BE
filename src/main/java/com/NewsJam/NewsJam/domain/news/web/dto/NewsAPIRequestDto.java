package com.NewsJam.NewsJam.domain.news.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class NewsAPIRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Keywords{
        List<String> keywords;
    }
}
