package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.global.paging.enums.SortStatus;
import com.NewsJam.NewsJam.global.paging.exception.InvalidSortStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsQueryServiceImpl implements NewsQueryService {
    private final NewsRepository newsRepository;

    @Override
    public Page<News> getHotTopicKeywordNewsPage(String keyword, SortStatus sortStatus, Integer page,
                                                 Integer pageSize) {
        Pageable pageable = toPageable(sortStatus, page, pageSize);
        Page<News> result = newsRepository.findDistinctByKeywordsWord(keyword, pageable);

        return result;
    }

    @Override
    public Page<News> getCategoryNewsPage(NewsCategory category, int page, int size,
                                          SortStatus sortStatus) {
        Pageable pageable = toPageable(sortStatus, page, size);
        return newsRepository.findByNewsCategory(category, pageable);

    }

    private Pageable toPageable(SortStatus sortStatus, Integer page, Integer pageSize) {
        Sort sort;
        if (sortStatus == SortStatus.LATEST) {
            sort = Sort.by(Sort.Direction.DESC, "createdAt");
        } else if (sortStatus == SortStatus.POPULAR) {
            sort = Sort.by(Sort.Direction.DESC, "viewCount");
        } else {
            throw new InvalidSortStatusException();
        }
        return PageRequest.of(page - 1, pageSize, sort);
    }


}
