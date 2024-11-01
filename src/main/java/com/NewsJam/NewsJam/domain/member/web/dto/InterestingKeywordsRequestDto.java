package com.NewsJam.NewsJam.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class InterestingKeywordsRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InterestingKeywordRequest {
        @Schema(description = "사용자의 관심 키워드 리스트", example="interestingKeywords=[\"연예\", \"스포츠\", \"의료\"]")
        List<String> interestingKeywords;
    }
}
