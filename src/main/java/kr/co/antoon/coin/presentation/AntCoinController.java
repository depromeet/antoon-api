package kr.co.antoon.coin.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.dto.CoinHistoryResponse;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "코인 API")
@RestController
@RequestMapping(value = "/api/v1/coins", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class AntCoinController {
    private final AntCoinClient antCoinClient;

    @ApiOperation(value = "코인 히스토리 조회 API", notes = SwaggerNote.GET_COIN_HISTORY)
    @GetMapping("/history")
    public ResponseEntity<CoinHistoryResponse> getHistory(@AuthUser AuthInfo info) {
        var response = antCoinClient.getCoinHistory(info.userId());
        return ResponseEntity.ok(response);
    }
}
