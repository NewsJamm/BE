package com.NewsJam.NewsJam.domain.news.service.scheduler;

import com.NewsJam.NewsJam.domain.chatbot.web.service.ChatBotService;
import com.NewsJam.NewsJam.domain.news.entity.Keyword;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.news.service.NewsService;
import com.NewsJam.NewsJam.domain.news.service.NewsVectorService;
import com.NewsJam.NewsJam.domain.news.service.TrendKeywordService;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO.VectorizeRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO.ExtractKeywordResponseDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO.VectorizeResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIRequestDto.Keywords;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIResponseDto.NewsData;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsSchedulerService {
    private final NewsService newsService;
    private final TrendKeywordService trendKeywordService;
    private final NewsVectorService newsVectorService;
    private final NewsRepository newsRepository;
    private final ChatBotService chatBotService;

    private static Queue<Long> newsIdQueue = new ConcurrentLinkedQueue<>();

    @Scheduled(fixedDelay = 180000)
    public void scheduled() {
        log.info(":::: 뉴스 스케줄러 실행 ::::");

        List<String> keywords = trendKeywordService.getTrendKeyword();

        Keywords keywordDTO = new Keywords(keywords);

        List<NewsData> news = newsService.getNews(keywordDTO);

        saveNews(news);

        // TODO 큐 시간 검증 구현

        log.info(":::: 뉴스 스케줄러 종료 ::::");
    }

    private void saveNews(List<NewsData> news) {
        // TODO 카테고리 별 분류
        for (NewsData newsData : news) {
            String originalLink = newsData.getOriginalLink();

            // 만약 이미 저장한 뉴스 데이터면 continue;
            if (newsRepository.findByOriginalLink(originalLink).isPresent()) {
                continue;
            }

            VectorizeResponseDTO vectorizeResponseDTO = vectorizeNews(newsData);

            NewsCategory newsCategory = chatBotService.getNewsCategory(newsData.getDescription());

            News saved = saveNewsEntity(newsData, vectorizeResponseDTO, newsCategory);

            newsIdQueue.offer(saved.getId());
        }
    }


    private VectorizeResponseDTO vectorizeNews(NewsData newsData) {
        VectorizeRequestDTO request = VectorizeRequestDTO.builder()
                .news_content(newsData.getDescription())
                .news_title(newsData.getTitle())
                .category(NewsCategory.경제)
                .build();

        return newsVectorService.vectorizeNewsVector(request);
    }

    private News saveNewsEntity(NewsData newsData, VectorizeResponseDTO vectorizeResponseDTO,
                                NewsCategory newsCategory) {
        News newsCreate = News.builder()
                .newsTitle(newsData.getTitle())
                .newsContent(newsData.getDescription())
                .originalLink(newsData.getOriginalLink())
                .newsCategory(newsCategory)
                .pubDate(newsData.getPubDate())
                .vectorIdx(vectorizeResponseDTO.getVectorIdx())
                .keywordList(new ArrayList<>())
                .build();

        for (ExtractKeywordResponseDTO keywordResponseDTO : vectorizeResponseDTO.getKeywordResponseList()) {
            String word = keywordResponseDTO.getWord();
            Keyword keyword = Keyword.builder()
                    .keyword(word)
                    .build();

            newsCreate.addKeyword(keyword);
        }
        return newsRepository.save(newsCreate);
    }
}
