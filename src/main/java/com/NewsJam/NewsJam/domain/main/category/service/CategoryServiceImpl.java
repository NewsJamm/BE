package com.NewsJam.NewsJam.domain.main.category.service;

import com.NewsJam.NewsJam.domain.main.category.service.CategoryService;
import com.NewsJam.NewsJam.domain.main.category.web.dto.CategoryResponseDto;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.exception.NewsNotExistException;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final NewsRepository newsRepository;

    @Override
    public CategoryResponseDto.response getNewsList(NewsCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsRepository.findByNewsCategory(category, pageable);

        if (newsPage.isEmpty()) {
            log.info("::News Not Found::");
            throw new NewsNotExistException(ErrorStatus._NEWS_NOT_EXIST);
        }

        List<CategoryResponseDto.newsList> newsList = new ArrayList<>();
        for (News news : newsPage.getContent()) {
            CategoryResponseDto.newsList responseNews = CategoryResponseDto.newsList.builder()
                    .url(news.getOriginalLink())
                    .title(news.getNewsTitle())
                    .puhDate(news.getPubDate())
                    .viewCnt(news.getViewCnt())
                    .build();

            newsList.add(responseNews);
        }

        return CategoryResponseDto.response.builder()
                .category(category)
                .newsList(newsList)
                .size(size)
                .page(page)
                .last(newsPage.isLast())
                .build();
    }
}
