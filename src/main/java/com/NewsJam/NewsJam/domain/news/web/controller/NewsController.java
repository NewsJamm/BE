package com.NewsJam.NewsJam.domain.news.web.controller;

import com.NewsJam.NewsJam.domain.news.converter.NewsConvertor;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.service.NewsQueryService;
import com.NewsJam.NewsJam.domain.news.service.scheduler.NewsSchedulerService;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.CategoryNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWord;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWordList;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.NewsViewData;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.PickNewsData;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.PickNewsPage;
import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import com.NewsJam.NewsJam.global.paging.validation.annotation.Pageable;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSException;


@Tag(name = "실시간 뉴스 데이터 API", description = "뉴스 데이터 관련 API")
@RestController
@RequestMapping("/api/news")
@Slf4j
@RequiredArgsConstructor
public class NewsController {
    private static final int RECOMMEND_NEWS_COUNT = 6;
    private final NewsSchedulerService newsSchedulerService;
    private final NewsQueryService newsQueryService;

    @Operation(summary = "핫 토픽 워드 클라우드 데이터 API 요청", description = "핫 토픽 워드 클라우드 내부 키워드, 태그 수 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = HotTopicWordList.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("hot-topic")
    public ApiResponse<NewsResponseDTO.HotTopicWordList> getHotTopicWords(
            @Valid @RequestParam(name = "count") @Schema(description = "조회할 단어의 개수 (인기순)", example = "10") Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }

        List<HotTopicWord> hotTopicWords = newsSchedulerService.getHotTopicWords(1, count);
        HotTopicWordList result = HotTopicWordList.builder()
                .word_list(hotTopicWords)
                .build();
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "핫 토픽 키워드 뉴스 데이터 API 요청", description = "워드 클라우드 내 키워드 클릭 시 보여줄 뉴스 데이터 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = HotTopicNewsPage.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("hot-topic/keyword")
    public ApiResponse<NewsResponseDTO.HotTopicNewsPage> getHotTopicKeywordNews(
            @Valid @RequestParam(name = "keyword") @NotNull(message = "단어를 입력해주세요.") @Schema(description = "키워드", example = "호날두") String keyword,
            @Pageable @RequestParam(name = "page") @Schema(description = "paging 에서 불러올 page 번호 (최소 1)", example = "2") Integer page,
            @Pageable @RequestParam(name = "pageSize") @Schema(description = "한 page 에서의 데이터 개수", example = "5") Integer pageSize) {

        Page<News> hotTopicKeywordNewsPage = newsQueryService.getHotTopicKeywordNewsPage(keyword, SortStatus.LATEST,
                page,
                pageSize);

        HotTopicNewsPage result = NewsConvertor.toHotTopicNewsPage(hotTopicKeywordNewsPage);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "카테고리별 뉴스 데이터 API 요청", description = "카테고리에 맞는 뉴스 데이터 페이징 검색 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = CategoryNewsPage.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("/category")
    public ApiResponse<NewsResponseDTO.CategoryNewsPage> getNewsPageWithCategory(
            @Valid @RequestParam(name = "category") @NotNull(message = "적절하지 않은 카테고리 입니다.") NewsCategory category,
            @Valid @RequestParam(name = "sortStatus") @NotNull(message = "적절하지 않은 정렬 기준입니다.") SortStatus sortStatus,
            @Pageable @RequestParam(name = "page") @Schema(description = "paging 에서 불러올 page 번호 (최소 1)", example = "1") Integer page,
            @Pageable @RequestParam(name = "pageSize") @Schema(description = "한 page 에서의 데이터 개수", example = "3") Integer pageSize) {
        Page<News> categoryNewsPage = newsQueryService.getCategoryNewsPage(category, page, pageSize, sortStatus);

        CategoryNewsPage result = NewsConvertor.toCategoryNewsPage(categoryNewsPage, category);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/pick")
    public ApiResponse<PickNewsPage> getPickNewsPage(
            @Pageable @RequestParam(name = "page") @Schema(description = "paging 에서 불러올 page 번호 (최소 1)", example = "1") Integer page,
            @Pageable @RequestParam(name = "pageSize") @Schema(description = "한 page 에서의 데이터 개수", example = "3") Integer pageSize
    ){
        List<HotTopicWord> hotTopicWords = newsSchedulerService.getHotTopicWords(page, pageSize);

        List<PickNewsData> pickNewsDataList = new ArrayList<>();
        for(int i = 0 ; i < hotTopicWords.size() ; i ++) {
            String keyword = hotTopicWords.get(i).getWord();

            List<News> content = newsQueryService.getHotTopicKeywordNewsPage(keyword,
                    SortStatus.POPULAR, 1, 1).getContent();

            if(content.isEmpty()){
               continue;
            }

            News news = content.get(0);

            NewsViewData pickNews = NewsConvertor.toNewsViewData(news);

            List<NewsViewData> recommendNewsList = newsQueryService.getRecommendNewsList(news.getVectorIdx(),
                    RECOMMEND_NEWS_COUNT);

            PickNewsData pickNewsData = NewsConvertor.toPickNewsData(keyword, pickNews, recommendNewsList);

            pickNewsDataList.add(pickNewsData);
        }

        int wordListSize = newsSchedulerService.getTopicWordSize();
        int totalPage = (int)Math.ceil((double)wordListSize / pageSize);

        PickNewsPage pickNewsPage = NewsConvertor.toPickNewsPage(pickNewsDataList, pageSize, (long) wordListSize,
                totalPage, wordPageIsFirst(page), wordPageIsLast(totalPage, page));

        return ApiResponse.onSuccess(pickNewsPage);
    }

    private boolean wordPageIsFirst(int pageNum){
        if(pageNum == 1) return true;
        return false;
    }

    private boolean wordPageIsLast(int totalPage, int pageNum){
        if(pageNum >= totalPage) return true;
        return false;
    }
}
