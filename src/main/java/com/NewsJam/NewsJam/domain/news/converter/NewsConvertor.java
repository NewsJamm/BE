package com.NewsJam.NewsJam.domain.news.converter;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.CategoryNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicNewsPage;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.HotTopicWord;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.NewsViewData;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.PickNewsData;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.PickNewsPage;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class NewsConvertor {
    public static HotTopicWord toHotTopicWord(String word, int count) {
        return HotTopicWord.builder()
                .word(word)
                .count(count)
                .build();
    }

    public static NewsResponseDTO.HotTopicNewsPage toHotTopicNewsPage(Page<News> news) {
        List<NewsViewData> newsList = news.getContent().stream()
                .map(data -> toNewsViewData(data))
                .collect(Collectors.toList());

        return HotTopicNewsPage.builder()
                .newsList(newsList)
                .isLast(news.isLast())
                .isFirst(news.isFirst())
                .totalPage(news.getTotalPages())
                .totalElements(news.getTotalElements())
                .listSize(news.getSize())
                .build();
    }

    public static NewsResponseDTO.NewsViewData toNewsViewData(News news) {
        return NewsViewData.builder()
                .id(news.getId())
                .title(news.getNewsTitle())
                .content(news.getNewsContent())
                .url(news.getOriginalLink())
                .publish_date(news.getPubDate())
                .build();
    }

    public static NewsResponseDTO.CategoryNewsPage toCategoryNewsPage(Page<News> news, NewsCategory category) {

        List<NewsViewData> newsViewDataList = news.getContent().stream()
                .map(NewsConvertor::toNewsViewData)
                .collect(Collectors.toList());

        return new CategoryNewsPage().builder()
                .category(category)
                .newsList(newsViewDataList)
                .isLast(news.isLast())
                .isFirst(news.isFirst())
                .totalPage(news.getTotalPages())
                .totalElements(news.getTotalElements())
                .listSize(news.getSize())
                .build();
    }

    public static PickNewsData toPickNewsData(String keyword, NewsViewData pickNews, List<NewsViewData> recommendNewsData){
        return PickNewsData.builder()
                .keyword(keyword)
                .pickNews(pickNews)
                .recommendNews(recommendNewsData)
                .build();
    }

    public static PickNewsPage toPickNewsPage(List<PickNewsData> pickNewsDataList, Integer listSize, Long totalElements, Integer totalPage,
                                              Boolean isFirst, Boolean isLast){
        return PickNewsPage.builder()
                .pickNewsDataList(pickNewsDataList)
                .listSize(listSize)
                .totalElements(totalElements)
                .totalPage(totalPage)
                .isFirst(isFirst)
                .isLast(isLast)
                .build();
    }
}
