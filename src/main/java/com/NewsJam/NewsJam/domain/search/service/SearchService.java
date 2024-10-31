package com.NewsJam.NewsJam.domain.search.service;

import com.NewsJam.NewsJam.domain.search.web.dto.SearchResultResponseDto;

public interface SearchService {
    SearchResultResponseDto.SearchResult search (String query);
}