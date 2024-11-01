package com.NewsJam.NewsJam.domain.news.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class NewsAPIRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Keywords {
        List<String> keywords;
    }
}
