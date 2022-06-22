package kr.co.antoon.graph.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.domain.vo.Period;
import kr.co.antoon.graph.dto.response.GraphScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "그래프 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_UTF_8)
public class GraphScoreController {
    private final GraphScoreSnapshotService graphScoreSnapshotService;

    @ApiOperation(value = "일 단위 그래프 API", notes = SwaggerNote.GRAPH_SCORES_DAY_READ_NOTE)
    @GetMapping("/webtoons/{webtoonId}/graph-scores/days")
    public ResponseEntity<GraphScoreResponse> getByDays(
            @PathVariable("webtoonId") Long webtoonId
    ) {
        var response = graphScoreSnapshotService.graphByDays(webtoonId, Period.of("day"));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "주 단위 그래프 API", notes = SwaggerNote.GRAPH_SCORES_WEEKENDS_READ_NOTE)
    @GetMapping("/webtoons/{webtoonId}/graph-scores/weekends")
    public ResponseEntity<GraphScoreResponse> getByWeekends(
            @PathVariable("webtoonId") Long webtoonId
    ) {
        var response = graphScoreSnapshotService.graphByMoreThanWeek(webtoonId, Period.of("weekend"));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "월 단위 그래프 API", notes = SwaggerNote.GRAPH_SCORES_MONTHS_READ_NOTE)
    @GetMapping("/webtoons/{webtoonId}/graph-scores/months")
    public ResponseEntity<GraphScoreResponse> getByMonths(
            @PathVariable("webtoonId") Long webtoonId
    ) {
        var response = graphScoreSnapshotService.graphByMoreThanWeek(webtoonId, Period.of("month"));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "3개월 단위 그래프 API", notes = SwaggerNote.GRAPH_SCORE_THREE_MONTH_READ_NOTE)
    @GetMapping("/webtoons/{webtoonId}/graph-scores/three-months")
    public ResponseEntity<GraphScoreResponse> getByThreeMonths(
            @PathVariable("webtoonId") Long webtoonId
    ) {
        var response = graphScoreSnapshotService.graphByMoreThanWeek(webtoonId, Period.of("three-month"));
        return ResponseEntity.ok(response);
    }
}
