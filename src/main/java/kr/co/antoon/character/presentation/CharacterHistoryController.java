package kr.co.antoon.character.presentation;

import io.swagger.annotations.ApiOperation;
import kr.co.antoon.character.facade.CharacterHistoryFacade;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
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
@RequestMapping("/api/v1/characters")
public class CharacterHistoryController {
    private final CharacterHistoryFacade characterHistoryFacade;

    @ApiOperation(value = "인물/커플 탑승 API", notes = SwaggerNote.JOINED_CHARACTER)
    @PatchMapping("/{characterId}")
    public ResponseEntity<Void> create(
            @PathVariable Long characterId,
            @AuthUser AuthInfo info
    ) {
        characterHistoryFacade.create(characterId, info.userId());
        return ResponseDto.noContent();
    }
}
