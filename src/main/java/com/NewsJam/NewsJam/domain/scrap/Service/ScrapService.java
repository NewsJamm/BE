package com.NewsJam.NewsJam.domain.scrap.Service;

import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapResponseDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoResponseDto;

public interface ScrapService {
    ScrapResponseDto.ScrapResponse scrap(ScrapRequestDto.ScrapRequest scrapRequestDto, Long memberId);

    ScrapUndoResponseDto.ScrapUndoResponse scrapUndo(ScrapUndoRequestDto.ScrapUndoRequest scrapUndoRequestDto, Long memberId);
}
