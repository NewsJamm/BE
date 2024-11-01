package com.NewsJam.NewsJam.domain.scrap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapUndoRequestDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapUndoRequest {
        String url;
    }
}
