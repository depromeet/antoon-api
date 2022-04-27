package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    private int joinMemberCount;

    private int leaveMemberCount;

    @Builder
    public Webtoon(String title, String content, String url, String thumbnail, Platform platform) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.thumbnail = thumbnail;
        this.platform = platform;
        this.joinMemberCount = 0;
        this.leaveMemberCount = 0;
        publish();
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

    public Webtoon plusJoinMemberCount() {
        this.joinMemberCount++;
        return this;
    }

    public Webtoon plusLeaveMemberCount() {
        this.leaveMemberCount++;
        return this;
    }

    public Webtoon minusJoimMemberCount() {
        this.joinMemberCount--;
        return this;
    }

    public void updateJoinCount() {
        this.joinMemberCount += 1;
    }
}