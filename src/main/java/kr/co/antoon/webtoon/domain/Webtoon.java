package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import static kr.co.antoon.common.util.CommonUtil.isNotEmpty;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "i_webtoon_id", columnList = "id", unique = true))
public class Webtoon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String webtoonUrl;

    @Column(nullable = false)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @Column(nullable = false)
    private int age;


    @Builder
    public Webtoon(String title, String content, String webtoonUrl, String thumbnail, Platform platform, int age) {
        this.title = title;
        this.content = content;
        this.webtoonUrl = webtoonUrl;
        this.thumbnail = thumbnail;
        this.platform = platform;
        this.age = age;
        publish();
    }

    public ActiveStatus getStatus() {
        return this.status;
    }

    public void publish() {
        changeStatus(ActiveStatus.PUBLISH);
    }

    public void pause() {
        changeStatus(ActiveStatus.PAUSE);
    }

    private void changeStatus(ActiveStatus status) {
        this.status = status;
    }

    public void update(String content, String thumbnail, String url) {
        if (isNotEmpty(content)) {
            this.content = content;
        }
        if (isNotEmpty(thumbnail)) {
            this.thumbnail = thumbnail;
        }
        if (isNotEmpty(url)) {
            this.webtoonUrl = url;
        }
    }
}
