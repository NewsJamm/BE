package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO.VectorizeResponseDTO;


public interface NewsVectorService {
    VectorizeResponseDTO vectorizeNewsVector(NewsVectorRequestDTO.VectorizeRequestDTO request);
    NewsVectorResponseDTO.RecommendVectorResponseDTO getRecommendVectorNews(
            NewsVectorRequestDTO.RecommendVectorRequestDTO request
    );
}
