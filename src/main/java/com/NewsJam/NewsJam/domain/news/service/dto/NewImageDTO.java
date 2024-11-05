package com.NewsJam.NewsJam.domain.news.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NewImageDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewsImageRequestDTO {
        private String news_url;
    }

    @Data
    public static class NewsImageResponseDTO {
        @JsonProperty("image_url")
        private String imageUrl;
    }

}
