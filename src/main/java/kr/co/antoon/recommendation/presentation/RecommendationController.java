package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import kr.co.antoon.recommendation.facade.RecommendationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping(value = "/api/v1/recommendations", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationFacade recommendationFacade;

    @ApiOperation(value = "탑승/하차", notes = SwaggerNote.RECOMMENDATION)
    @PatchMapping("/{webtoonId}")
    public ResponseEntity<RecommendationResponse> createLeaveStatus(
            @PathVariable Long webtoonId,
            @AuthUser AuthInfo info,
            @RequestParam("status") String status) {
        RecommendationResponse response = recommendationFacade.saveOrUpdate(status, info.userId(), webtoonId);
        return ResponseDto.ok(response);
    }
}