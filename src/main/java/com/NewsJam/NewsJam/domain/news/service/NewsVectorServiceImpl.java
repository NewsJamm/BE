package com.NewsJam.NewsJam.domain.news.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.NewsJam.NewsJam.domain.news.converter.NewsVectorConverter;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsVectorServiceImpl implements NewsVectorService{
	@Value("${news.vectorize.base_url}")
	private String baseUrl;
	private WebClient webClient;

	private final NewsRepository newsRepository;
	public News saveNewsWithVector(String newsTitle, String newsContent, NewsCategory newsCategory){
		NewsVectorRequestDTO.VectorizeRequestDTO request = NewsVectorConverter.toVectorizeRequestDTO(newsTitle,
			newsContent, newsCategory);

		Mono<NewsVectorResponseDTO.VectorizeResponseDTO> mono = vectorizeNewsVector(request);

		return mono
			.map(input -> News.builder()
				.index(input.getVectorIdx().longValue())
				.newsTitle(newsTitle)
				.newsContent(newsContent)
				.newsCategory(newsCategory)
				.build())
			.map(entity -> newsRepository.save(entity))
			.block();
	}

	public Mono<NewsVectorResponseDTO.VectorizeResponseDTO> vectorizeNewsVector(NewsVectorRequestDTO.VectorizeRequestDTO request){
		if(this.webClient == null){
			webClient = initWebClient();
		}

		return webClient.post()
			.uri("/api/news")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(NewsVectorResponseDTO.VectorizeResponseDTO.class);
	}


	public WebClient initWebClient(){
		return WebClient.builder()
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.baseUrl(baseUrl)
			.clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
			.build();
	}

}
