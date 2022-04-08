package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Webtoon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String writer;

    private String url;

    private String thumbnail;

    private String genre;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Builder
    public Webtoon(String title, String content, String writer, String url, String thumbnail, String genre, Platform platform) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.url = url;
        this.thumbnail = thumbnail;
        this.genre = genre;
        this.platform = platform;
    }

    public void update(String title, String content, String writer, String thumbnail, String url, String genre) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.thumbnail = thumbnail;
        this.url = url;
        this.genre = genre;
    }

    public boolean isEqualsTitle(String title) {
        return this.title.equals(title);
    }
}