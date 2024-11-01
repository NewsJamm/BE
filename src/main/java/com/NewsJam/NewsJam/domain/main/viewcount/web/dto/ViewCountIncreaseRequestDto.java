package com.NewsJam.NewsJam.domain.main.viewcount.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ViewCountIncreaseRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        String url;
    }
}
