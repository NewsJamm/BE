package com.NewsJam.NewsJam.domain.mypage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewScrapListRequestDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewScrapDto {
        private Long memberId;
    }
}
