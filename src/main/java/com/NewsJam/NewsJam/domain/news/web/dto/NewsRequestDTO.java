package com.NewsJam.NewsJam.domain.news.web.dto;

import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import com.NewsJam.NewsJam.global.paging.validation.annotation.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(title = "NewsRequestDTO", description = "뉴스 데이터 요청 DTO")
public class NewsRequestDTO {

    @Data
    public class HotTopicKeywordNews {
        @NotNull(message = "키워드를 입력해주세요.")
        private String keyword;

        @NotNull(message = "정렬 기준은 필수입니다.")
        @Schema(description = "정렬 기준 : LATEST, POPULAR 중 선택", example = "LATEST")
        private SortStatus sort;

        @Pageable
        @NotNull(message = "page값은 필수입니다.")
        @Schema(description = "paging 에서 불러올 page 번호", example = "2")
        private Integer page;

        @Pageable
        @NotNull(message = "page 크기 값은 필수입니다.")
        @Schema(description = "한 page 에서의 데이터 개수", example = "5")
        private Integer page_size;
    }

}
