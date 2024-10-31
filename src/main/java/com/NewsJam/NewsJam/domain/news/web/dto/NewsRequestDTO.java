package com.NewsJam.NewsJam.domain.news.web.dto;

import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import com.NewsJam.NewsJam.global.paging.validation.annotation.Pageable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class NewsRequestDTO {

    @Data
    public class HotTopicKeywordNews {
        @NotNull(message = "키워드를 입력해주세요.")
        private String keyword;

        @NotNull(message = "정렬 기준은 필수입니다.")
        private SortStatus sort;

        @Pageable
        @NotNull(message = "page값은 필수입니다.")
        private Integer page;

        @Pageable
        @NotNull(message = "page 크기 값은 필수입니다.")
        private Integer page_size;
    }

}
