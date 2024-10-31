package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import org.springframework.data.domain.Page;

public interface NewsQueryService {
    Page<News> getHotTopicKeywordNewsPage(String keyword, SortStatus sortStatus, Integer page,
                                          Integer pageSize);

}
