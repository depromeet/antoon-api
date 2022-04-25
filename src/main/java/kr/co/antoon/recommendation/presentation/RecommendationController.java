package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.recommendation.application.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping("/api/v1/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @ApiOperation(value = "탑승해요 API", notes = "")
    @PatchMapping("/join/{webtoonId}")
    public Long joinUpdate(@PathVariable Long webtoonId) {
        // TODO: AuthenticationUtil에서 가져오도록 추후 변경
        Long memberId = 1L;
        return recommendationService.joinUpdate(webtoonId, memberId);
    }

    @ApiOperation(value = "하차해요 API", notes = "")
    @PatchMapping("/leave/{webtoonId}")
    public Long leaveUpdate(@PathVariable Long webtoonId) {
        // TODO: AuthenticationUtil에서 가져오도록 추후 변경
        Long memberId = 1L;
        return recommendationService.leaveUpdate(webtoonId, memberId);
    }
}
