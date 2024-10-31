package com.NewsJam.NewsJam.domain.news.repository;

import com.NewsJam.NewsJam.domain.news.entity.News;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByOriginalLink(String originalLink);
    Optional<List<News>> findByTitleContainingByPubDateDesc(String title);
}
