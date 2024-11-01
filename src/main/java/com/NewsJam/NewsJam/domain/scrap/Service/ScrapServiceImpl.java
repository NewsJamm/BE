package com.NewsJam.NewsJam.domain.scrap.Service;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.member.exception.UserNotExistException;
import com.NewsJam.NewsJam.domain.member.repository.MemberRepository;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.scrap.entity.Scrap;
import com.NewsJam.NewsJam.domain.scrap.exception.ScrapNotExistException;
import com.NewsJam.NewsJam.domain.scrap.repository.ScrapRepository;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapResponseDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoResponseDto;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapServiceImpl implements ScrapService {
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public ScrapResponseDto.ScrapResponse scrap(ScrapRequestDto.ScrapRequest scrapRequestDto, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()){
            log.info("::Member Not Exist !!!::");
            throw new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST);
        }
        Member scrapMember = member.get();

        Scrap newScrap = Scrap.builder()
                .newsUrl(scrapRequestDto.getUrl())
                .member(scrapMember)
                .build();
        scrapRepository.save(newScrap);

        List<Scrap> newScrapList = scrapMember.getScrapList();
        newScrapList.add(newScrap);

        return ScrapResponseDto.ScrapResponse.builder()
                .url(scrapRequestDto.getUrl())
                .memberId(scrapMember.getId())
                .build();
    }

    @Override
    public ScrapUndoResponseDto.ScrapUndoResponse scrapUndo(ScrapUndoRequestDto.ScrapUndoRequest scrapUndoRequestDto, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()){
            log.info("::Member Not Exist !!!::");
            throw new UserNotExistException(ErrorStatus._MEMBER_NOT_EXIST);
        }
        Optional<Scrap> undoScrap = scrapRepository.findByNewsUrl(scrapUndoRequestDto.getUrl());
        if(undoScrap.isEmpty()){
            log.info("::Scrap Not Exist !!!::");
            throw new ScrapNotExistException(ErrorStatus._SCRAP_NOT_EXIST);
        }
        Scrap scrap = undoScrap.get();
        Member scrapUndoMember = member.get();
        if(scrapUndoMember.getScrapList().contains(scrap)){
            scrapUndoMember.getScrapList().remove(scrap);
        }

        scrapRepository.delete(scrap);
        memberRepository.save(scrapUndoMember);

        ScrapUndoResponseDto.ScrapUndoResponse response = ScrapUndoResponseDto.ScrapUndoResponse.builder()
                .memberId(scrapUndoMember.getId())
                .url(scrapUndoRequestDto.getUrl())
                .build();

        return response;
    }

}
