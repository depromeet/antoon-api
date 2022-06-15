package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.vote.domain.vo.VoteCategory;
import kr.co.antoon.vote.domain.vo.VoteType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_item")
public class VoteItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String content;

    private String imageUrl;

    private LocalDateTime votingEndTime;

    @Enumerated(EnumType.STRING)
    private VoteCategory category;

    @Enumerated(EnumType.STRING)
    private VoteType type;

    private Long votingCount;

    private Long votingCountPercent;
}
