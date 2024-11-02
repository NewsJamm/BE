package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsResponseDTO.NewsViewData;
import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import java.util.List;
import org.springframework.data.domain.Page;

public interface NewsQueryService {
    Page<News> getHotTopicKeywordNewsPage(String keyword, SortStatus sortStatus, Integer page,
                                          Integer pageSize);

    Page<News> getCategoryNewsPage(NewsCategory category, int page, int size,
                                   SortStatus sortStatus);


    List<NewsViewData> getRecommendNewsList(Long vectorIdx, Integer count);

}
