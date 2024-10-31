package com.NewsJam.NewsJam.domain.mypage.service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.exception.UserNotExistException;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.mypage.web.dto.ScrapNewsListResponseDto;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.exception.NewsNotExistException;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.scrap.entity.Scrap;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService {
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;

    @Override
    public ScrapNewsListResponseDto.ScrapNewsList scrapNewsList(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            throw new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST);
        }
        Member found = findMember.get();
        List<Scrap> scrapList = found.getScrapList();

        List<ScrapNewsListResponseDto.newsList> newList =  new ArrayList<>();
        for(Scrap scrap : scrapList) {
            Optional<News> news = newsRepository.findByOriginalLink(scrap.getNewsUrl());
            if(news.isEmpty()) {
                throw new NewsNotExistException(ErrorStatus._NEWS_NOT_EXIST);
            }
            News getNews = news.get();
            ScrapNewsListResponseDto.newsList newNews = ScrapNewsListResponseDto.newsList.builder()
                    .title(getNews.getNewsTitle())
                    .pubDate(getNews.getPubDate())
                    .url(getNews.getOriginalLink())
                    .build();

            newList.add(newNews);
        }

        return ScrapNewsListResponseDto.ScrapNewsList.builder()
                .scrapList(newList)
                .build();
    }
}
