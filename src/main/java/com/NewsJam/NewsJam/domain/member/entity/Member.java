package com.NewsJam.NewsJam.domain.member.entity;

import com.NewsJam.NewsJam.domain.member.enums.AuthProvider;
import com.NewsJam.NewsJam.domain.scrap.entity.Scrap;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "login_pw")
    private String loginPw;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "auth_provider")
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "scrap_list")
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scrapList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities;

    @Column(name = "keywords")
    @ElementCollection
    private List<String> interestingKeywords = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    public void addScrap(Scrap scrap) {
        scrapList.add(scrap);
        scrap.changeMember(this);
    }

    public void addAuthority(Authority authority) {
        authorities.add(authority);
        authority.setMember(this);
    }

    public void addInterestingKeywords(List<String> keywords) {
        interestingKeywords.addAll(keywords);
    }

    public void setRole(Authority authority) {
        authorities.add(authority);
        authority.setMember(this);
    }

    public Authority getRole() {
        return authorities.get(0);
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
