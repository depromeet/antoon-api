package kr.co.antoon.subject.presentation;

import kr.co.antoon.subject.dto.response.CharacterResponse;
import kr.co.antoon.subject.dto.response.CoupleResponse;
import kr.co.antoon.subject.facade.SubjectFacade;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/subjects")
public class SubjectController {
    private final SubjectFacade subjectFacade;

    @GetMapping("/characters")
    public ResponseEntity<CharacterResponse> getCharactersByTopUpper(
            @AuthUser AuthInfo info
    ) {
        var response = subjectFacade.getCharactersByTopUpper(info.userId());
        return ResponseDto.ok(response);
    }

    @GetMapping("/couples")
    public ResponseEntity<CoupleResponse> getCouplesByTopUpper(
            @AuthUser AuthInfo info
    ) {
        var response = subjectFacade.getCouplesByTopUpper(info.userId());
        return ResponseDto.ok(response);
    }
}
