package kr.co.antoon.character.presentation;

import kr.co.antoon.character.facade.CharacterVoteFacade;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteCharacterController {
    private final CharacterVoteFacade voteCharacterFacade;

    @PatchMapping("/{characterId}")
    public ResponseEntity<?> create(
            @PathVariable Long characterId,
            @AuthUser AuthInfo info
    ) {
        voteCharacterFacade.create(characterId, info.userId());
        return ResponseDto.noContent();
    }
}
