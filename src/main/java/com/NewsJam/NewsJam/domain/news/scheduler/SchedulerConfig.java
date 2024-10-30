package com.NewsJam.NewsJam.domain.news.scheduler;

import com.NewsJam.NewsJam.domain.news.service.NewsService;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIRequestDto;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {
    private final NewsService newsService;

    @Scheduled(fixedDelay = 180000)
    public List<NewsAPIResponseDto.NewsData> scheduled(NewsAPIRequestDto.Keywords keywords) {

        return newsService.getNews(keywords);
    }
}
