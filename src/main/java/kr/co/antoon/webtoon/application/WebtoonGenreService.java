package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonGenre;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.infrastructure.WebtoonGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebtoonGenreService {
    private final WebtoonGenreRepository webtoonGenreRepository;

    @Transactional
    public void saveAll(List<WebtoonGenre> webtoonGenres) {
        webtoonGenreRepository.saveAll(webtoonGenres);
    }

    @Transactional(readOnly = true)
    public List<Category> findCategoryByWebtoonId(Long webtoonId) {
        List<WebtoonGenre> webtoonGenre = webtoonGenreRepository.findByWebtoonId(webtoonId);
        ArrayList<Category> genre = new ArrayList<>();
        for(WebtoonGenre g : webtoonGenre) {
            genre.add(g.getCategory());
        }
        return genre;
    }
}