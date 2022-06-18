package kr.co.antoon.graph.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.graph.dto.response.TopRankResponse;
import kr.co.antoon.graph.facade.TopRankFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "랭킹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/top-ranks", produces = APPLICATION_JSON_UTF_8)
public class TopRankController {
    private final TopRankFacade topRankFacade;

    @ApiOperation(value = "랭킹 조회 API", notes = "*추후 작성 필요*")
    @GetMapping("/webtoons")
    public ResponseEntity<TopRankResponse> getTopRankWebtoons() {
        var response = topRankFacade.findTopRank();
        return ResponseDto.ok(response);
    }
}