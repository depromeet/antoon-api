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

    @Lob
    private String content;

    private String url;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private Boolean active;

    @Builder
    public Webtoon(String title, String content, String url, String thumbnail, Platform platform) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.thumbnail = thumbnail;
        this.platform = platform;
        this.active = true;
    }

    public void update(String title, String content, String thumbnail, String url) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public boolean isEqualsTitle(String title) {
        return this.title.equals(title);
    }
}