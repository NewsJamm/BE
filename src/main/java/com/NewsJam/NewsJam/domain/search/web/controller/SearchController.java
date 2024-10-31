package com.NewsJam.NewsJam.domain.search.web.controller;

import com.NewsJam.NewsJam.domain.search.service.SearchService;
import com.NewsJam.NewsJam.domain.search.web.dto.SearchResultResponseDto;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public ApiResponse<SearchResultResponseDto.SearchResult> search(@RequestParam String query) {
        log.info("Search query: {}", query);
        SearchResultResponseDto.SearchResult response = searchService.search(query);

        return ApiResponse.onSuccess(response);
    }
}