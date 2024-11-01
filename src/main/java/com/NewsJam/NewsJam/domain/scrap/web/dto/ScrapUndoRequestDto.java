package com.NewsJam.NewsJam.domain.scrap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapUndoRequestDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapUndoRequest {
        @Schema(description = "스크랩 취소를 원하는 뉴스의 URL", example = "Undo.com")
        String url;
        @Schema(description = "스크랩 취소를 원하는 유저의 고유값(로그인 아이디와는 별개)", example = "1")
        Long memberId;
    }
}
