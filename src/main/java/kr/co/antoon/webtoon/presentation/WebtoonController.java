package kr.co.antoon.webtoon.presentation;

import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;
import kr.co.antoon.webtoon.facade.WebtoonFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/webtoons")
public class WebtoonController {
    private final WebtoonFacade webtoonFacade;

    @GetMapping("/{webtoonId}")
    public ResponseEntity<WebtoonDetailDto> selectWebtoon (@PathVariable("webtoonId") Long id) {
        return ResponseDto.ok(webtoonFacade.get(id));
    }
}
