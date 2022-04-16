package kr.co.antoon.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.antoon.oauth.application.CustomOAuth2UserService;
import kr.co.antoon.oauth.application.TokenService;
import kr.co.antoon.oauth.dto.TokenDto;
import kr.co.antoon.oauth.dto.UserDto;
import kr.co.antoon.oauth.dto.UserRequestMapper;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Customizer;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        // 최초 로그인 시 회원가입
        User optionalUser = userRepository.findByEmail(userDto.getEmail());
        TokenDto token;
        if(optionalUser == null) {
            token = tokenService.generateToken(userDto.getEmail(), "USER");
            log.info("Token : ", token);

            User user = User.builder()
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .picture(userDto.getPicture())
                    .accessToken(token.getToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
            userRepository.save(user);
        } else {
            token = TokenDto.builder()
                    .token(optionalUser.getAccessToken())
                    .refreshToken(optionalUser.getRefreshToken())
                    .build();
        }

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, TokenDto tokenDto)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("AccessToken", tokenDto.getToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(tokenDto));
        writer.flush();
    }
}


