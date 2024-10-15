package com.NewsJam.NewsJam.domain.news.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO;

import reactor.core.publisher.Mono;
class NewsVectorServiceImplTest {
	@Mock
	private NewsRepository newsRepository;

	@Mock
	private WebClient webClient;

	@InjectMocks
	private NewsVectorServiceImpl newsVectorService;

	@BeforeEach
	public void init(){
		MockitoAnnotations.openMocks(this);
	}
	@DisplayName("벡터화 데이터 저장 테스트")
	@Test
	void testSaveNewsWithVector(){
		//given
		String newsTitle = "한국 경제, 위기설 불식하며 안정세 유지";
		String newsContent = "최근 한국 경제에 대한 위기설이 제기되었지만, 전문가들은 이를 과장된 주장이라고 평가하고 있다. 주요 경제 지표들은 안정적인 성장을 보이고 있으며, 정부의 적절한 정책 대응으로 경제는 견고한 기반을 유지하고 있다. 따라서 한국 경제는 위기보다는 지속적인 발전 가능성을 보여주고 있다.";
		NewsCategory newsCategory = NewsCategory.경제;

		// 예상 응답
		NewsVectorResponseDTO.VectorizeResponseDTO responseDTO = new NewsVectorResponseDTO.VectorizeResponseDTO();
		responseDTO.setVectorIdx(1L);
		List<NewsVectorResponseDTO.ExtractKeywordResponseDTO> keywordResponseList = new ArrayList<>();
		NewsVectorResponseDTO.ExtractKeywordResponseDTO keyword = new NewsVectorResponseDTO.ExtractKeywordResponseDTO();
		keyword.setScore(0.8);
		keyword.setWord("경제");
		keywordResponseList.add(keyword);
		responseDTO.setKeywordResponseList(keywordResponseList);

		webClient = newsVectorService.initWebClient();

		// newsRepository 모의 설정
		News savedNews = News.builder()
			.index(responseDTO.getVectorIdx())
			.newsTitle(newsTitle)
			.newsContent(newsContent)
			.newsCategory(newsCategory)
			.build();

		when(newsRepository.save(any(News.class))).thenReturn(savedNews);

		//when
		News news = newsVectorService.saveNewsWithVector(newsTitle, newsContent, newsCategory);

		//then
		assertEquals(news.getIndex(), 1L);
		assertEquals(news.getId(), 1L);
		assertEquals(news.getNewsTitle(), newsTitle);
	}

	@Test
	void testVectorizeNewsVector(){
		//given
		String newsTitle = "한국 경제, 위기설 불식하며 안정세 유지";
		String newsContent = "최근 한국 경제에 대한 위기설이 제기되었지만, 전문가들은 이를 과장된 주장이라고 평가하고 있다. 주요 경제 지표들은 안정적인 성장을 보이고 있으며, 정부의 적절한 정책 대응으로 경제는 견고한 기반을 유지하고 있다. 따라서 한국 경제는 위기보다는 지속적인 발전 가능성을 보여주고 있다.";
		NewsCategory newsCategory = NewsCategory.경제;

		NewsVectorRequestDTO.VectorizeRequestDTO request = NewsVectorRequestDTO.VectorizeRequestDTO.builder()
			.news_title(newsTitle)
			.news_content(newsContent)
			.category(NewsCategory.경제)
			.build();

		webClient = newsVectorService.initWebClient();

		// when
		Mono<NewsVectorResponseDTO.VectorizeResponseDTO> mono = newsVectorService.vectorizeNewsVector(request);

		NewsVectorResponseDTO.VectorizeResponseDTO response = mono.block();

		//then
		assertNotNull(response);
	}

}
