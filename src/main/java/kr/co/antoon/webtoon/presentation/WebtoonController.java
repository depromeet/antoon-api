package kr.co.antoon.webtoon.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import kr.co.antoon.webtoon.dto.response.WebtoonStatusResponse;
import kr.co.antoon.webtoon.facade.WebtoonStatusFacade;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.dto.request.WebtoonSearchRequest;
import kr.co.antoon.webtoon.facade.WebtoonFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "웹툰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/webtoons", produces = APPLICATION_JSON_UTF_8)
public class WebtoonController {
    private final WebtoonFacade webtoonFacade;
    private final WebtoonStatusFacade webtoonStatusFacade;
    private final WebtoonService webtoonService;


    @ApiOperation(value = "웹툰 상세 조회 API", notes = SwaggerNote.WEBTOON_READ_DETAIL)
    @GetMapping(value = "/{webtoonId}")
    public ResponseEntity<?> getWebtoonById(
            @PathVariable("webtoonId") Long webtoonId
    ) {
        var response = webtoonService.findDetailWebtoon(webtoonId);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "연재 웹툰 요일별 조회 API", notes = SwaggerNote.WEBTOON_DAY_READ_NOTE)
    @GetMapping("/days/{day}")
    public ResponseEntity<?> getWebtoonByDay(
            @PathVariable String day,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        var response = webtoonFacade.getWebtoonByDay(pageable, day);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "웹툰 전체 조회 API", notes = SwaggerNote.WEBTOON_SEARCH)
    @GetMapping
    public ResponseEntity<?> getWebtoons() {
        var response = webtoonService.searchAll();
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "장르별 활성화된 웹툰 조회 API", notes = SwaggerNote.WEBTOON_READ_GENRE)
    @GetMapping(value = "/genres")
    public ResponseEntity<?> getWebtoonsByGenreAndStatus(
            @RequestParam("genre") String genre,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        var response = webtoonFacade.getWebtoonsGenreAndStatus(pageable, genre);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "장르별 top3 웹툰 조회 API", notes = SwaggerNote.WEBTOON_READ_GENRES)
    @GetMapping(value = "/genres/top3")
    public ResponseEntity<?> getWebtoonsByGenres() {
        var response = webtoonFacade.getGenreAndThumbnail();
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "현재 기준으로 상승 중인 TOP 10 웹툰 조회 API", notes = SwaggerNote.WEBTOON_READ_RANKING_NOTE)
    @GetMapping(value = "/top-upper")
    public ResponseEntity<?> getWebtoonsByTopUpper() {
        var response = webtoonFacade.getWebtoonsByTopUpper();
        return ResponseDto.ok(response);
    }

    @Deprecated
    @ApiOperation(value = "연령대별 인기 웹툰 조회 API [MOCK UP]")
    @GetMapping("/ages")
    public ResponseEntity<?> age() {
        var response = webtoonFacade.getAges();
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "seach용 웹툰 조회")
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody WebtoonSearchRequest request) {
        var response = webtoonFacade.search(request);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "웹툰 탑승/하차", notes = SwaggerNote.WEBTOON_JOIN)
    @PatchMapping("/{webtoonId}/join")
    public ResponseEntity<WebtoonStatusResponse> createWebtoonStatus(
            @PathVariable Long webtoonId,
            @AuthUser AuthInfo info,
            @RequestParam("status") WebtoonStatusType status
    ) {

        var response = webtoonStatusFacade.saveOrUpdate(status, info.userId(), webtoonId);
        return ResponseDto.ok(response);
    }
}

