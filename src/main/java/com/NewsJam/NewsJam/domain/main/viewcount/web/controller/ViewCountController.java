package com.NewsJam.NewsJam.domain.main.viewcount.web.controller;

import com.NewsJam.NewsJam.domain.main.viewcount.service.ViewCountService;
import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseRequestDto;
import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseResponseDto;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ViewCountController {
    private final ViewCountService viewCountService;

    @PostMapping("/increaseViewCnt")
    public ApiResponse<ViewCountIncreaseResponseDto.response> viewCountIncrease(@RequestBody ViewCountIncreaseRequestDto.Request requestDto){
        log.info("url = {}", requestDto.getUrl());

        ViewCountIncreaseResponseDto.response response = viewCountService.increaseViewCount(requestDto.getUrl());
        return ApiResponse.onSuccess(response);
    }

}
