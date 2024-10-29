package com.NewsJam.NewsJam.domain.news.service.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

public class NewsVectorResponseDTO {


	@Data
	@ToString
	public static class VectorizeResponseDTO{
		Long vectorIdx;
		List<ExtractKeywordResponseDTO> keywordResponseList;
	}

	@Data
	@ToString
	public static class ExtractKeywordResponseDTO{
		String word;
		Double score;
	}



}
