package kr.co.antoon.oauth.presentation;

import kr.co.antoon.oauth.application.OauthService;
import kr.co.antoon.oauth.dto.KakaoOauthToken;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @ResponseBody
    @GetMapping("/login/oauth2/code/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
        KakaoOauthToken kakaoOauthToken = oauthService.getKakaoAccessToken(code);
        oauthService.createKakaoUser(kakaoOauthToken.getAccess_token());
    }

    @GetMapping("/test")
    public String index() {
        return "Hello World";
    }
}
