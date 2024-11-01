package com.NewsJam.NewsJam.domain.scrap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapUndoResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScrapUndoResponse{
        @Schema(description = "스크랩 취소를 진행한 뉴스의 URL", example = "Undo.com")
        String url;
        @Schema(description = "스크랩 취소를 진행한 유저의 고유값(로그인 아이디와는 별개)", example = "1")
        Long memberId;
    }
}
