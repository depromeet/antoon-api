package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonGenre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GenreCategory genreCategory;

    private Long webtoonId;

    public WebtoonGenre(GenreCategory genreCategory, Long webtoonId) {
        this.genreCategory = genreCategory;
        this.webtoonId = webtoonId;
    }

    public String getGenreCategoryDescription() {
        return this.genreCategory.getDescription();
    }
}