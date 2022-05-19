package kr.co.antoon.graph.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.domain.vo.Period;
import kr.co.antoon.graph.dto.response.GraphScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "그래프 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GraphScoreController {
    private final GraphScoreSnapshotService graphScoreSnapshotService;

    @ApiOperation(value = "그래프 API", notes = "추후 작성 필요")
    @GetMapping("/webtoons/{webtoonId}/graph-scores")
    public ResponseEntity<GraphScoreResponse> get(
            @PathVariable("webtoonId") Long webtoonId,
            @RequestParam("period") String period
    ) {
        var response = graphScoreSnapshotService.graph(webtoonId, Period.of(period));
        return ResponseEntity.ok(response);
    }
}