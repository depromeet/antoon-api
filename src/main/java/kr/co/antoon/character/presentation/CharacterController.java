package kr.co.antoon.character.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.character.dto.response.CharacterDetailResponse;
import kr.co.antoon.character.dto.response.CharacterResponse;
import kr.co.antoon.character.facade.CharacterFacade;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(tags = "인물/커플 조회 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CharacterController {
    private final CharacterFacade characterFacade;

    @ApiOperation(value = "인물/커플 실시간 차트 조회 API", notes = SwaggerNote.GET_CHARACTER_RANK)
    @GetMapping("/top-ranks/characters")
    public ResponseEntity<CharacterResponse> getCharacterTopUpper(
            @RequestParam("type") String type,
            @AuthUser AuthInfo info
    ) {
        var response = characterFacade.getTopUpper(type, info);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "인물/커플 상세조회 API", notes = SwaggerNote.GET_CHARACTER_DETAIL)
    @GetMapping("/characters/{characterId}")
    public ResponseEntity<CharacterDetailResponse> getCharacter(
            @PathVariable Long characterId,
            @RequestParam("type") String type,
            @AuthUser AuthInfo info
    ) {
        var response = characterFacade.getCharacterDetail(
                characterId,
                type,
                info
        );
        return ResponseDto.ok(response);
    }
}
