package com.NewsJam.NewsJam.domain.search.service;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.exception.NewsNotExistException;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.search.web.dto.SearchResultResponseDto;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final NewsRepository newsRepository;

    // title 기준으로 검색, 최신순으로 정렬
    @Override
    public SearchResultResponseDto.SearchResult search(String query) {
        Optional<List<News>> titleContainingNews = newsRepository.findByTitleContainingByPubDateDesc(query);
        if (titleContainingNews.isEmpty()) {
            log.info(":: NEWS NOT EXIST ::");
            throw new NewsNotExistException(ErrorStatus._NEWS_NOT_EXIST);
        }
        List<News> found = titleContainingNews.get();

        List<SearchResultResponseDto.FoundNews> results = new ArrayList<>();
        for(News news : found) {
            SearchResultResponseDto.FoundNews foundNews = SearchResultResponseDto.FoundNews.builder()
                    .title(news.getNewsTitle())
                    .pubDate(news.getPubDate())
                    .url(news.getOriginalLink())
                    .build();

            results.add(foundNews);
        }
        return SearchResultResponseDto.SearchResult.builder()
                .foundNews(results)
                .build();
    }

}
