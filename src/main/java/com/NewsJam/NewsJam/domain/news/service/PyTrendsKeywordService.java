package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.service.dto.TrendKeywordResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PyTrendsKeywordService implements TrendKeywordService {
    @Value("${ai.server.base_url}")
    private static String BASE_URL;
    private static WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
            .build();

    @Override
    public List<String> getTrendKeyword() {
        return webClient.get()
                .uri("/api/keyword")
                .retrieve()
                .bodyToMono(TrendKeywordResponseDTO.class)
                .map(TrendKeywordResponseDTO::getTrendingKeywords)
                .block();
    }
}
