package kr.co.antoon.character.presentation;

import kr.co.antoon.character.dto.response.PersonaResponse;
import kr.co.antoon.character.dto.response.CoupleResponse;
import kr.co.antoon.character.facade.CharacterFacade;
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
@RequestMapping("/api/v1/top-ranks")
public class CharacterController {
    private final CharacterFacade characterFacade;

    @GetMapping("/personas")
    public ResponseEntity<PersonaResponse> getCharactersByTopUpper(
            @AuthUser AuthInfo info
    ) {
        var response = characterFacade.getPersonasByTopUpper(info.userId());
        return ResponseDto.ok(response);
    }

    @GetMapping("/couples")
    public ResponseEntity<CoupleResponse> getCouplesByTopUpper(
            @AuthUser AuthInfo info
    ) {
        var response = characterFacade.getCouplesByTopUpper(info.userId());
        return ResponseDto.ok(response);
    }
}
