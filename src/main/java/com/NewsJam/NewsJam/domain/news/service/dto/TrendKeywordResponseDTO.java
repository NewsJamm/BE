package com.NewsJam.NewsJam.domain.news.service.dto;

import java.util.List;

public class TrendKeywordResponseDTO {
    private List<String> trending_keywords;

    public List<String> getTrendingKeywords() {
        return trending_keywords;
    }

    public void setTrendingKeywords(List<String> trending_keywords) {
        this.trending_keywords = trending_keywords;
    }
}
