package com.NewsJam.NewsJam.domain.news.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.ToString;

public class NewsVectorResponseDTO {


    @Data
    @ToString
    public static class VectorizeResponseDTO {
        @JsonProperty("vector_idx")
        private Long vectorIdx;
        @JsonProperty("keywords")
        private List<ExtractKeywordResponseDTO> keywordResponseList;
    }

    @Data
    @ToString
    public static class ExtractKeywordResponseDTO {
        private String word;
        private Double score;
    }

    @Data
    @ToString
    public static class RecommendVectorResponseDTO {
        private List<Integer> indices;
    }

}
