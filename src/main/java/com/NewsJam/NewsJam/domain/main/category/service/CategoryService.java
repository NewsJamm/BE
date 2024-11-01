package com.NewsJam.NewsJam.domain.main.category.service;

import com.NewsJam.NewsJam.domain.main.category.web.dto.CategoryResponseDto;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;

public interface CategoryService {
    CategoryResponseDto.response getNewsList(NewsCategory category, int page, int size);
}
