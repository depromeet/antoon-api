package kr.co.antoon.graph.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.graph.dto.TopRankResponse;
import kr.co.antoon.graph.facade.TopRankFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "랭킹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/top-ranks")
public class TopRankController {
    private final TopRankFacade topRankFacade;

    @ApiOperation(value = "랭킹 조회 API", notes = "*추후 작성 필요*")
    @GetMapping
    public ResponseEntity<TopRankResponse> getTopRankWebtoons() {
        return ResponseDto.ok(topRankFacade.findTopRank());
    }
}