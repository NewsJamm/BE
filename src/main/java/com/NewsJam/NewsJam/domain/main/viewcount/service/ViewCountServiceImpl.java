package com.NewsJam.NewsJam.domain.main.viewcount.service;

import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseResponseDto;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.exception.NewsNotExistException;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewCountServiceImpl implements ViewCountService {

    private final NewsRepository newsRepository;

    @Override
    public ViewCountIncreaseResponseDto.ViewCountIncreaseResponse increaseViewCount(String url) {
        Optional<News> found = newsRepository.findByOriginalLink(url);

        if(found.isEmpty()){
            log.info("News not found");
            throw new NewsNotExistException(ErrorStatus._NEWS_NOT_EXIST);
        }
        News news = found.get();

        news.increaseViewCnt();
        log.info("Increase view count");

        return ViewCountIncreaseResponseDto.ViewCountIncreaseResponse.builder()
                .id(news.getId())
                .url(news.getOriginalLink())
                .viewCnt(news.getViewCnt())
                .build();
    }


}
