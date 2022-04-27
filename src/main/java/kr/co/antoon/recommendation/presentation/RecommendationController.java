package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.recommendation.application.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping("/api/v1/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @ApiOperation(value = "탑승해요")
    @PostMapping("/join/{webtoonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean joinUpdate(@PathVariable Long webtoonId) {
        Long memberId = 1L; // TODO: AuthenticationUtil에서 가져오도록 추후 변경
        return recommendationService.updateJoinStatus(webtoonId, memberId);
    }

    @ApiOperation(value = "하차해요")
    @PostMapping("/leave/{webtoonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean leaveUpdate(@PathVariable Long webtoonId) {
        Long memberId = 1L; // TODO: AuthenticationUtil에서 가져오도록 추후 변경
        return recommendationService.updateLeaveStatus(webtoonId, memberId);
    }
}
