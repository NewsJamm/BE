package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.service.dto.NewImageDTO.NewsImageRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewImageDTO.NewsImageResponseDTO;
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
public class ImageCrawlingService implements NewsImageService {
    @Value("${ai.server.base_url}")
    private String baseUrl;
    private static WebClient webClient;


    @Override
    public String getImageUrl(String newsUrl) {
        if (webClient == null) {
            webClient = initWebClient();
        }

        NewsImageRequestDTO request = NewsImageRequestDTO.builder()
                .news_url(newsUrl)
                .build();

        return webClient.post()
                .uri(baseUrl + "/api/image")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(NewsImageResponseDTO.class)
                .block()
                .getImageUrl();
    }

    public WebClient initWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .build();
    }

}
