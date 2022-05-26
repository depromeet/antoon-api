package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
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

    // TODO : 탑승과 하차를 하나의 API에서 진행하는 건 어떨까요?
    @ApiOperation(value = "탑승해요", notes = SwaggerNote.RECOMMENDATION_JOIN)
    @PatchMapping("/join/{webtoonId}")
    public ResponseEntity<Void> createJoinStatus(@PathVariable Long webtoonId, @AuthUser AuthInfo info) {
        recommendationFacade.saveOrUpdateJoin(info.userId(), webtoonId);
        return ResponseDto.noContent();
    }

    @ApiOperation(value = "하차해요", notes = SwaggerNote.RECOMMENDATION_LEAVE)
    @PatchMapping("/leave/{webtoonId}")
    public ResponseEntity<Void> createLeaveStatus(@PathVariable Long webtoonId, @AuthUser AuthInfo info) {
        recommendationFacade.saveOrUpdateLeave(info.userId(), webtoonId);
        return ResponseDto.noContent();
    }
}