package com.NewsJam.NewsJam.domain.news.web.dto;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Schema(title = "NewsResponseDTO", description = "뉴스 데이터 응답 DTO")
public class NewsResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotTopicWordList {
        @Schema(description = "워드클라우드 핫 토픽 단어 목록")
        private List<HotTopicWord> word_list;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotTopicWord {
        @Schema(description = "키워드 단어", example = "호날두")
        private String word;
        @Schema(description = "키워드 연관 뉴스 수", example = "36")
        private Integer count;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotTopicNewsPage {
        @Schema(description = "핫 토픽 키워드 관련 뉴스 데이터 목록")
        private List<NewsViewData> newsList;
        @Schema(description = "페이지 내 데이터 수", example = "3")
        private Integer listSize;
        @Schema(description = "전체 페이지 수", example = "5")
        private Integer totalPage;
        @Schema(description = "전체 데이터 수", example = "36")
        private Long totalElements;
        @Schema(description = "첫 페이지 인지", example = "true")
        private Boolean isFirst;
        @Schema(description = "마지막 페이지 인지", example = "false")
        private Boolean isLast;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewsViewData {
        @Schema(description = "뉴스 고유 번호", example = "5")
        private Long id;
        @Schema(description = "뉴스 제목", example = "호날두와 메시, 누가 더 강력할까?")
        private String title;
        @Schema(description = "뉴스 내용", example = "호날두가 메시보다 키가 크고, 덩치가 좋아서 호날두가 강력하다.")
        private String content;
        @Schema(description = "뉴스 출간 날짜", example = "Fri, 01 Nov 2024 15:00:00 +0900")
        private String publish_date;
        @Schema(description = "뉴스 원본 링크", example = "https://www.pressian.com/pages/articles/2024110113442365418?utm_source=naver&utm_medium=search")
        private String url;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CategoryNewsPage {
        @Schema(description = "선택된 뉴스 카테고리", example = "스포츠")
        NewsCategory category;
        @Schema(description = "카테고리 관련 뉴스 데이터 목록")
        private List<NewsViewData> newsList;
        @Schema(description = "페이지 내 데이터 수", example = "3")
        private Integer listSize;
        @Schema(description = "전체 페이지 수", example = "5")
        private Integer totalPage;
        @Schema(description = "전체 데이터 수", example = "36")
        private Long totalElements;
        @Schema(description = "첫 페이지 인지", example = "true")
        private Boolean isFirst;
        @Schema(description = "마지막 페이지 인지", example = "false")
        private Boolean isLast;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PickNewsPage{
        @Schema(description = "Pick 뉴스 데이터 목록")
        private List<PickNewsData> pickNewsDataList;
        @Schema(description = "페이지 내 데이터 수", example = "3")
        private Integer listSize;
        @Schema(description = "전체 페이지 수", example = "5")
        private Integer totalPage;
        @Schema(description = "전체 데이터 수", example = "36")
        private Long totalElements;
        @Schema(description = "첫 페이지 인지", example = "true")
        private Boolean isFirst;
        @Schema(description = "마지막 페이지 인지", example = "false")
        private Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PickNewsData{
        @Schema(description = "뉴스 키워드")
        public String keyword;

        @Schema(description = "Pick 뉴스 데이터")
        public NewsViewData pickNews;

        @Schema(description = "Pick 뉴스 관련 추천 뉴스")
        public List<NewsViewData> recommendNews;
    }


}
