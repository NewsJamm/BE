package com.NewsJam.NewsJam.domain.search.web.controller;

import com.NewsJam.NewsJam.domain.search.service.SearchService;
import com.NewsJam.NewsJam.domain.search.web.dto.SearchResultResponseDto;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "검색 API", description = "뉴스 검색 기능 API")
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "뉴스 검색", description = "제목을 기준으로, 최신순 뉴스 검색 기능")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "검색 성공",
                    content = @Content(schema = @Schema(implementation = SearchResultResponseDto.SearchResult.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @GetMapping("/search")
    public ApiResponse<SearchResultResponseDto.SearchResult> search(
            @RequestParam
            @Parameter(description = "검색어", example = "손흥민") String query) {
        log.info("Search query: {}", query);
        SearchResultResponseDto.SearchResult response = searchService.search(query);
        return ApiResponse.onSuccess(response);
    }
}
