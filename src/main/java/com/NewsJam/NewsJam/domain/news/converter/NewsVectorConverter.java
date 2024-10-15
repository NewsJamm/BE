package com.NewsJam.NewsJam.domain.news.converter;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO;

public class NewsVectorConverter {
	public static NewsVectorRequestDTO.VectorizeRequestDTO toVectorizeRequestDTO(String newsTitle, String newsContent, NewsCategory newsCategory){
		return NewsVectorRequestDTO.VectorizeRequestDTO.builder()
			.news_title(newsTitle)
			.news_content(newsContent)
			.category(newsCategory)
			.build();
	}
}
