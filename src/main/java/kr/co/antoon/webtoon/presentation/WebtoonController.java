package kr.co.antoon.webtoon.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;
import kr.co.antoon.webtoon.facade.WebtoonFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "웹툰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/webtoons")
public class WebtoonController {
    private final WebtoonFacade webtoonFacade;

    @ApiOperation(value = "웹툰 상세 조회 API", notes = "추후 note 추가할 예정")
    @GetMapping("/{webtoonId}")
    public ResponseEntity<WebtoonDetailDto> getWebtoonById(@PathVariable("webtoonId") Long webtoonId) {
        return ResponseDto.ok(webtoonFacade.getWebtoon(webtoonId));
    }
}