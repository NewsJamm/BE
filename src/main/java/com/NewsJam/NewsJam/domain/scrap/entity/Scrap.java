package com.NewsJam.NewsJam.domain.scrap.entity;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scrap")
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_url", nullable = false)
    private String newsUrl;

    @Column(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberId;

    public void setMember(Member member) {
        this.memberId = member;
    }
}