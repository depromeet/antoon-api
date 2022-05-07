package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
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

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @Builder
    public Webtoon(String title, String content, String url, String thumbnail, Platform platform) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.thumbnail = thumbnail;
        this.platform = platform;
        publish();
    }

    public ActiveStatus getStatus(){
        return this.status;
    }

    public void publish() {
        changeStatus(ActiveStatus.PUBLISH);
    }

    private void changeStatus(ActiveStatus status) {
        this.status = status;
    }

    public void update(String title, String content, String thumbnail, String url) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.url = url;
    }
}