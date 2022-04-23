package kr.co.antoon.webtoon.presentation;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/webtoon")
public class WebtoonController {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;

    @GetMapping("/detail/{id}")
    public WebtoonDetailDto webtoonDetail(@PathVariable("id") Long id) {
        //id, title, content, url, thumbnail, platform, active
        Webtoon webtoon = webtoonService.findById(id);

        //writer, genre
        List<String> writer = webtoonWriterService.findNameByWebtoonId(id);
        List<Category> genre = webtoonGenreService.findCategoryByWebtoonId(id);

        WebtoonDetailDto webtoonDetailDto = WebtoonDetailDto.builder()
                .title(webtoon.getTitle())
                .content(webtoon.getContent())
                .writer(writer)
                .url(webtoon.getUrl())
                .thumbnail(webtoon.getThumbnail())
                .genre(genre)
                .active(webtoon.getActive())
                .platform(webtoon.getPlatform())
                .build();

        return webtoonDetailDto;
    }
}

