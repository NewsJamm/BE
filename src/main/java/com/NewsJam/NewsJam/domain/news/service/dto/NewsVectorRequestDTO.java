package com.NewsJam.NewsJam.domain.news.service.dto;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NewsVectorRequestDTO {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VectorizeRequestDTO {
		private String news_title;
		private String news_content;
		private NewsCategory category;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RecommendVectorRequestDTO{
		private Long faiss_index;
		private Integer recommend_count;
	}
}
