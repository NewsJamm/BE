package com.NewsJam.NewsJam.domain.news.web.controller;

import com.NewsJam.NewsJam.domain.news.converter.NewsConvertor;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.service.NewsQueryService;
import com.NewsJam.NewsJam.domain.news.service.scheduler.NewsSchedulerService;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWord;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWordList;
import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import com.NewsJam.NewsJam.global.paging.validation.annotation.Pageable;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 */
@RestController
@RequestMapping("/api/news")
@Slf4j
@RequiredArgsConstructor
public class NewsController {
    private final NewsSchedulerService newsSchedulerService;
    private final NewsQueryService newsQueryService;

    @GetMapping("hot-topic")
    public ApiResponse<NewsResponseDTO.HotTopicWordList> getHotTopicWords(
            @Valid @RequestParam(name = "count") Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }

        List<HotTopicWord> hotTopicWords = newsSchedulerService.getHotTopicWords(count);
        HotTopicWordList result = HotTopicWordList.builder()
                .word_list(hotTopicWords)
                .build();
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("hot-topic/keyword")
    public ApiResponse<NewsResponseDTO.HotTopicNewsPage> getHotTopicKeywordNews(
            @Valid @RequestParam(name = "keyword") @NotNull(message = "단어를 입력해주세요.") String keyword,
            @Pageable @RequestParam(name = "page") Integer page,
            @Pageable @RequestParam(name = "pageSize") Integer pageSize) {

        Page<News> hotTopicKeywordNewsPage = newsQueryService.getHotTopicKeywordNewsPage(keyword, SortStatus.LATEST,
                page,
                pageSize);

        HotTopicNewsPage result = NewsConvertor.toHotTopicNewsPage(hotTopicKeywordNewsPage);
        return ApiResponse.onSuccess(result);
    }
}
