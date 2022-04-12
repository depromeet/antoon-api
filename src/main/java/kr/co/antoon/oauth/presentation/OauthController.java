package kr.co.antoon.oauth.presentation;

import com.nimbusds.jwt.JWT;
import kr.co.antoon.member.domain.User;
import kr.co.antoon.member.infrastructure.UserRepository;
import kr.co.antoon.oauth.application.OauthService;
import kr.co.antoon.oauth.dto.KakaoOauthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;
    private final UserRepository userRepository;
    private final Environment env;

    @ResponseBody
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) { //프론트에서 넘겨준 코드
        System.out.println(code);
        KakaoOauthToken kakaoOauthToken = oauthService.getKakaoAccessToken(code);
        User user = oauthService.createKakaoUser(kakaoOauthToken.getAccess_token());
        Optional<User> users = userRepository.findByEmail(user.getEmail());

        // 서비스에 등록된 회원이 아니라면
        if (users.isEmpty()) {
            User newUser = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .profileImg(user.getProfileImg())
                    .refreshToken(kakaoOauthToken.getRefresh_token())
                    .build();
            // 회원가입
            userRepository.save(newUser);
        } else {
            // 로그인
            return new ResponseEntity<String>(kakaoOauthToken.getRefresh_token(), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
