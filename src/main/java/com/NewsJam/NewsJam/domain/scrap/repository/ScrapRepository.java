package com.NewsJam.NewsJam.domain.scrap.repository;

import com.NewsJam.NewsJam.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByNewsUrl(String url);
}
