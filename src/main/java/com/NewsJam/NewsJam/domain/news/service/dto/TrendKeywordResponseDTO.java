package com.NewsJam.NewsJam.domain.news.service.dto;

import java.util.List;
import lombok.Data;

@Data
public class TrendKeywordResponseDTO {
    private List<String> trending_keywords;

}
