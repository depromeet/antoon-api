package kr.co.antoon.recommendation.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import kr.co.antoon.recommendation.facade.RecommendationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "탑승/하차 API")
@RestController
@RequestMapping(value = "/api/v1/recommendations", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationFacade recommendationFacade;
    private final AntCoinService antCoinService;

    @ApiOperation(value = "탑승/하차", notes = SwaggerNote.RECOMMENDATION)
    @PatchMapping("/{webtoonId}")
    public ResponseEntity<RecommendationResponse> createLeaveStatus(
            @PathVariable Long webtoonId,
            @AuthUser AuthInfo info,
            @RequestParam("status") RecommendationStatus status
    ) {
        // TODO 해당 로직 처리는 여기서 하는게 이상해보이네요 response가 두번 사용되는 것도...
        var response = recommendationFacade.saveOrUpdate(status, info.userId(), webtoonId);
        response = antCoinService.joinWebtoon(info.userId(), webtoonId, response, status);
        return ResponseDto.ok(response);
    }
}
