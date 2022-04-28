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
    @PatchMapping("/join/{userId}/{webtoonId}")
    public ResponseEntity<Void> updateJoinStatus(@PathVariable Long webtoonId, @PathVariable Long userId) {
        // Long userId = 1L;  // TODO: 테스트를 위해 userId도 받음. Authentication에서 USER ID 조회하도록 변경 필요
        recommendationService.updateJoinStatus(userId, webtoonId);
        return ResponseDto.noContent();
    }

    @ApiOperation(value = "하차해요", notes = SwaggerNote.RECOMMENDATION_LEAVE)
    @PatchMapping("/leave/{webtoonId}")
    public ResponseEntity<Void> updateLeaveStatus(@PathVariable Long webtoonId, @PathVariable Long userId) {
        // Long userId = 1L;  // TODO: 테스트를 위해 userId도 받음. Authentication에서 USER ID 조회하도록 변경 필요
        recommendationService.updateLeaveStatus(userId, webtoonId);
        return ResponseDto.noContent();
    }
}
