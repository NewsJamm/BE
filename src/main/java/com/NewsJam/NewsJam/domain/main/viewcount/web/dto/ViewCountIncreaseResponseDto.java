package com.NewsJam.NewsJam.domain.main.viewcount.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewCountIncreaseResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class response {
        private Long id;
        private String url;
        private Long viewCnt;
    }
}
