package kr.co.toonzip.user.presentation;

import kr.co.toonzip.security.CurrentUser;
import kr.co.toonzip.security.oauth2.domain.UserPrincipal;
import kr.co.toonzip.user.domain.User;
import kr.co.toonzip.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) throws Exception {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(Exception::new);
    }
}
