package com.NewsJam.NewsJam.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class InterestingKeywordsRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        List<String> interestingKeywords;
    }
}
