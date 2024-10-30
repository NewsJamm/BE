package com.NewsJam.NewsJam.domain.news.repository;

import com.NewsJam.NewsJam.domain.news.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
