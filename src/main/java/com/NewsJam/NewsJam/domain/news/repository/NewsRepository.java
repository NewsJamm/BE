package com.NewsJam.NewsJam.domain.news.repository;

import com.NewsJam.NewsJam.domain.news.entity.News;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByOriginalLink(String originalLink);

    Optional<List<News>> findByNewsTitleContainingOrderByPubDateDesc(String query);

    @Query("SELECT n FROM News n JOIN n.keywordList keyword WHERE keyword.keyword = :keyword")
    Page<News> findDistinctByKeywordsWord(String keyword, Pageable pageable);


}
