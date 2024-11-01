package com.NewsJam.NewsJam.domain.main.category.web.controller;

import com.NewsJam.NewsJam.domain.main.category.service.CategoryService;
import com.NewsJam.NewsJam.domain.main.category.web.dto.CategoryResponseDto;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public ApiResponse<CategoryResponseDto.response> getCategory(@RequestParam NewsCategory category,@RequestParam int page, @RequestParam int size) {
        CategoryResponseDto.response response = categoryService.getNewsList(category, page, size);

        return ApiResponse.onSuccess(response);
    }
}
