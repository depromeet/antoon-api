package kr.co.antoon.character.presentation;

import io.swagger.annotations.ApiOperation;
import kr.co.antoon.character.application.CharacterImageService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.domain.vo.CharacterImageType;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.reqeust.CharacterImageRequest;
import kr.co.antoon.character.dto.reqeust.CharacterRequest;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CharacterController {
    private final CharacterFacade characterFacade;
    private final CharacterService characterService;
    private final CharacterImageService characterImageService;

    @ApiOperation(value = "인물/커플 실시간 차트 조회 API", notes = SwaggerNote.GET_CHARACTER_RANK)
    @GetMapping("/top-ranks/characters")
    public ResponseEntity<CharacterResponse> getCharacterTopUpper(
            @RequestParam("type") String type,
            @AuthUser AuthInfo info
    ) {
        CharacterResponse response = characterFacade.getTopUpper(type, info.userId());
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "인물/커플 상세조회 API", notes = SwaggerNote.GET_CHARACTER_DETAIL)
    @GetMapping("/characters/{characterId}")
    public ResponseEntity<CharacterDetailResponse> getCharacter(
            @PathVariable Long characterId,
            @RequestParam("type") String type,
            @AuthUser AuthInfo info
    ) {
        CharacterDetailResponse response = characterFacade.getCharacterDetail(
                characterId,
                type,
                info.userId()
        );
        return ResponseDto.ok(response);
    }
}
