package kr.co.antoon.webtoon.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.dto.response.WebtoonDetailResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonResponse;
import kr.co.antoon.webtoon.facade.WebtoonFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public ResponseEntity<WebtoonDetailResponse> getWebtoonById(@PathVariable("webtoonId") Long webtoonId) {
        return ResponseDto.ok(webtoonFacade.getWebtoon(webtoonId));
    }

    @ApiOperation(value = "웹툰 전체 조회 API", notes = SwaggerNote.WEBTOON_SEARCH)
    @GetMapping
    public ResponseEntity<WebtoonResponse> getWebtoons() {
        return ResponseDto.ok(webtoonService.searchAll());
    }
}