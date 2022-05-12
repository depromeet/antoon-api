package kr.co.antoon.webtoon.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.PageDto;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.webtoon.dto.response.*;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.dto.response.WebtoonAllResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonDayResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonResponse;
import kr.co.antoon.webtoon.facade.WebtoonFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "웹툰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/webtoons")
public class WebtoonController {
    private final WebtoonFacade webtoonFacade;
    private final WebtoonService webtoonService;

    @ApiOperation(value = "웹툰 상세 조회 API", notes = SwaggerNote.WEBTOON_READ_DETAIL)
    @GetMapping(value = "/{webtoonId}")
    public ResponseEntity<WebtoonResponse> getWebtoonById(@PathVariable("webtoonId") Long webtoonId) {
        return ResponseDto.ok(webtoonFacade.getWebtoon(webtoonId));
    }

    @ApiOperation(value = "연재 웹툰 요일별 조회 API", notes = SwaggerNote.WEBTOON_DAY_READ_NOTE)
    @GetMapping("/days/{day}")
    public ResponseEntity<PageDto<WebtoonDayResponse>> getWebtoonByDay(
            @PathVariable String day,
            @PageableDefault(size = 12, page = 0) Pageable pageable
    ) {
        var response = webtoonFacade.getWebtoonByDay(pageable, day);
        return PageDto.ok(response);
    }

    @ApiOperation(value = "웹툰 전체 조회 API", notes = SwaggerNote.WEBTOON_SEARCH)
    @GetMapping
    public ResponseEntity<WebtoonAllResponse> getWebtoons() {
        return ResponseDto.ok(webtoonService.searchAll());
    }

    @ApiOperation(value = "장르별 활성화된 웹툰 조회 API", notes = SwaggerNote.WEBTOON_READ_GENRE)
    @GetMapping(value = "/genres/{genre}")
    public ResponseEntity<PageDto<WebtoonGenreResponse>> getWebtoonsByGenreAndStatus(
            @PathVariable("genre") String genre,
            @PageableDefault(size = 12, page = 0) Pageable pageable) {
        var response = webtoonFacade.getWebtoonsGenreAndStatus(pageable, genre);
        return PageDto.ok(response);
    }

    @ApiOperation(value = "장르별 top3 웹툰 조회 API")
    @GetMapping(value = "/genres")
    public ResponseEntity<WebtoonGenreAllResponse> getWebtoonsByGenres() {
        return ResponseDto.ok(webtoonFacade.getWebtoonsGenres());
    }
}