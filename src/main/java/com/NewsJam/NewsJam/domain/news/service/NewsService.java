package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIRequestDto;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIResponseDto;
import java.util.List;

public interface NewsService {
    String getDate();

    String getParsedString(String rawTitle);

    List<NewsAPIResponseDto.NewsData> getNews(NewsAPIRequestDto.Keywords keywords);
}
