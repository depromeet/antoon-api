package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.recommendation.facade.RecommendationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping(value = "/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationFacade recommendationFacade;

    @ApiOperation(value = "탑승해요", notes = SwaggerNote.RECOMMENDATION_JOIN)
    @PatchMapping("/join/{webtoonId}")
    public ResponseEntity<Void> updateJoinStatus(@PathVariable Long webtoonId, @AuthUser Long userId) {
        recommendationFacade.updateJoinStatus(userId, webtoonId);
        return ResponseDto.noContent();
    }

    @ApiOperation(value = "하차해요", notes = SwaggerNote.RECOMMENDATION_LEAVE)
    @PatchMapping("/leave/{webtoonId}")
    public ResponseEntity<Void> updateLeaveStatus(@PathVariable Long webtoonId, @AuthUser Long userId) {
        recommendationFacade.updateLeaveStatus(userId, webtoonId);
        return ResponseDto.noContent();
    }
}
