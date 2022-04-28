package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.recommendation.application.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @ApiOperation(value = "탑승해요", notes = SwaggerNote.RECOMMENDATION_JOIN)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/join/{webtoonId}")
    public boolean updateJoinStatus(@PathVariable Long webtoonId) {
        Long userId = 1L;  // TODO: Authentication에서 USER ID 조회하도록 변경
        return recommendationService.updateJoinStatus(userId, webtoonId);
    }

    @ApiOperation(value = "하차해요", notes = SwaggerNote.RECOMMENDATION_LEAVE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/leave/{webtoonId}")
    public boolean updateLeaveStatus(@PathVariable Long webtoonId) {
        Long userId = 1L;  // TODO: Authentication에서 USER ID 조회하도록 변경
        return recommendationService.updateLeaveStatus(userId, webtoonId);
    }
}
