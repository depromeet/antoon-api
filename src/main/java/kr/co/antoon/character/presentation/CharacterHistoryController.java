package kr.co.antoon.character.presentation;

import kr.co.antoon.character.facade.CharacterHistoryFacade;
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
@RequestMapping("/api/v1/top-ranks")
public class CharacterHistoryController {
    private final CharacterHistoryFacade characterHistoryFacade;

    @PatchMapping("/{itemId}")
    public ResponseEntity<Void> create(
            @PathVariable Long itemId,
            @AuthUser AuthInfo info
    ) {
        characterHistoryFacade.create(itemId, info.userId());
        return ResponseDto.noContent();
    }
}
