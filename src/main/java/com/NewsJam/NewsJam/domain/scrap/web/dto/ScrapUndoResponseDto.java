package com.NewsJam.NewsJam.domain.scrap.web.dto;

import lombok.Builder;

public class ScrapUndoResponseDto {

    @Builder
    public static class ScrapUndoResponse{
        Long scrapId;
        Long memberId;
    }
}
