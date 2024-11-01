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
		String news_title;
		String news_content;
		NewsCategory category;
	}
}
