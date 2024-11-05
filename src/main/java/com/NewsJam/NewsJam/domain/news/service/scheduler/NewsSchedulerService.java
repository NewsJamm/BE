package com.NewsJam.NewsJam.domain.news.service.scheduler;

import com.NewsJam.NewsJam.domain.chatbot.web.service.ChatBotService;
import com.NewsJam.NewsJam.domain.news.converter.NewsConvertor;
import com.NewsJam.NewsJam.domain.news.entity.Keyword;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.news.service.NewsImageService;
import com.NewsJam.NewsJam.domain.news.service.NewsService;
import com.NewsJam.NewsJam.domain.news.service.NewsVectorService;
import com.NewsJam.NewsJam.domain.news.service.TrendKeywordService;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO.VectorizeRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO.ExtractKeywordResponseDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO.VectorizeResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIRequestDto.Keywords;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIResponseDto.NewsData;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import lombok.Data;
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
    private final NewsImageService newsImageService;

    private static Queue<Long> newsIdQueue = new LinkedList<>();
    private static List<Word> wordList = new ArrayList<>();
    private static Map<String, Word> wordAddressMap = new HashMap<>();

    @Scheduled(fixedDelay = 600000)
    public void scheduled() {
        log.info(":::: 뉴스 스케줄러 실행 ::::");

        try {
            List<String> keywords = trendKeywordService.getTrendKeyword();

            Keywords keywordDTO = new Keywords(keywords);

            List<NewsData> news = newsService.getNews(keywordDTO);

            saveNews(news);
        } catch (Exception e) {
            log.error("오류 발생", e);
        }

        // TODO 큐 시간 검증 구현

        Collections.sort(wordList);

        log.info(":::: 뉴스 스케줄러 종료 ::::");
    }


    public List<NewsResponseDTO.HotTopicWord> getHotTopicWords(int page, int wordCount) {
        List<NewsResponseDTO.HotTopicWord> words = new ArrayList<>();
        int len = Math.min(page * wordCount, wordList.size());
        for (int i = (page - 1) * wordCount; i < len; i++) {
            Word word = wordList.get(i);
            words.add(NewsConvertor.toHotTopicWord(word.getKeyword(), word.getNewsCount()));
        }

        return words;
    }

    public int getTopicWordSize() {
        return wordList.size();
    }

    private void saveNews(List<NewsData> news) {
        for (NewsData newsData : news) {
            String originalLink = newsData.getOriginalLink();

            // 만약 이미 저장한 뉴스 데이터면 continue;
            if (newsRepository.findByOriginalLink(originalLink).isPresent()) {
                continue;
            }

            NewsCategory category = chatBotService.getNewsCategory(newsData.getTitle());

            String image_url = newsImageService.getImageUrl(newsData.getOriginalLink());

            VectorizeResponseDTO vectorizeResponseDTO = vectorizeNews(newsData, category);

            News saved = saveNewsEntity(newsData, vectorizeResponseDTO, category, image_url);

            newsIdQueue.offer(saved.getId());

            for (ExtractKeywordResponseDTO keywordResponseDTO : vectorizeResponseDTO.getKeywordResponseList()) {
                String keyword = keywordResponseDTO.getWord();
                addKeyword(keyword);
            }
        }

    }

    private void addKeyword(String keyword) {
        if (wordAddressMap.containsKey(keyword)) {
            Word word = wordAddressMap.get(keyword);
            word.increaseCount();
        } else {
            Word word = new Word(keyword);
            wordAddressMap.put(keyword, word);
            wordList.add(word);
        }
    }


    private VectorizeResponseDTO vectorizeNews(NewsData newsData, NewsCategory category) {
        VectorizeRequestDTO request = VectorizeRequestDTO.builder()
                .news_content(newsData.getDescription())
                .news_title(newsData.getTitle())
                .category(category)
                .build();

        return newsVectorService.vectorizeNewsVector(request);
    }

    private News saveNewsEntity(NewsData newsData, VectorizeResponseDTO vectorizeResponseDTO,
                                NewsCategory newsCategory, String imageUrl) {
        News newsCreate = News.builder()
                .newsTitle(newsData.getTitle())
                .newsContent(newsData.getDescription())
                .originalLink(newsData.getOriginalLink())
                .newsCategory(newsCategory)
                .pubDate(newsData.getPubDate())
                .vectorIdx(vectorizeResponseDTO.getVectorIdx())
                .viewCnt(0L)
                .imageUrl(imageUrl)
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


@Data
class Word implements Comparable<Word> {
    private String keyword;
    private int newsCount;

    public Word(String keyword) {
        this.keyword = keyword;
        this.newsCount = 1;
    }

    @Override
    public int compareTo(Word o) {
        return o.newsCount - this.newsCount;
    }

    public void increaseCount() {
        newsCount++;
    }

    public void decreaseCount() {
        newsCount--;
    }
}
