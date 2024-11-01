package com.NewsJam.NewsJam.domain.news.converter;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicNews;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWord;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

public class NewsConvertor {
    public static HotTopicWord toHotTopicWord(String word, int count) {
        return HotTopicWord.builder()
                .word(word)
                .count(count)
                .build();
    }

    public static NewsResponseDTO.HotTopicNewsPage toHotTopicNewsPage(Page<News> news) {
        List<HotTopicNews> newsList = new ArrayList<>();
        for (News newsData : news.getContent()) {
            HotTopicNews topicNews = HotTopicNews.builder()
                    .id(newsData.getId())
                    .title(newsData.getNewsTitle())
                    .content(newsData.getNewsContent())
                    .url(newsData.getOriginalLink())
                    .publish_date(newsData.getPubDate())
                    .build();
            newsList.add(topicNews);
        }

        return HotTopicNewsPage.builder()
                .news(newsList)
                .isLast(news.isLast())
                .isFirst(news.isFirst())
                .totalPage(news.getTotalPages())
                .totalElements(news.getTotalElements())
                .listSize(news.getSize())
                .build();
    }
}
