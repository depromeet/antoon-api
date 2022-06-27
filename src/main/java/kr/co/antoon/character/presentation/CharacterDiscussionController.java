package kr.co.antoon.character.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.character.application.CharacterDiscussionService;
import kr.co.antoon.character.dto.reqeust.CharacterDiscussionRequest;
import kr.co.antoon.character.dto.response.CharacterDiscussionResponse;
import kr.co.antoon.character.facade.CharacterDiscussionFacade;
import kr.co.antoon.character.facade.CharacterDiscussionLikeFacade;
import kr.co.antoon.common.dto.PageDto;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인물/커플 댓글 API")
@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
@Slf4j
public class CharacterDiscussionController {
    private final CharacterDiscussionFacade characterDiscussionFacade;
    private final CharacterDiscussionService characterDiscussionService;
    private final CharacterDiscussionLikeFacade characterDiscussionLikeFacade;

    @ApiOperation(value = "댓글 달기 API", notes = SwaggerNote.CHARACTER_DISCUSSION_CREATE_NOTE)
    @PostMapping("/{characterId}/discussions")
    public ResponseEntity<CharacterDiscussionResponse> create(
            @PathVariable Long characterId,
            @Validated @RequestBody CharacterDiscussionRequest request,
            @AuthUser AuthInfo info
    ) {
        var response = characterDiscussionFacade.register(info.userId(), characterId, request);
        return ResponseDto.created(response);
    }

    @ApiOperation(value = "댓글 단건 조회 API", notes = SwaggerNote.CHARACTER_DISCUSSION_READ_ONE_NOTE)
    @GetMapping("/discussions/{discussionId}")
    public ResponseEntity<CharacterDiscussionResponse> findOne(
            @PathVariable Long discussionId,
            @AuthUser AuthInfo info
    ) {
        var response = characterDiscussionFacade.findById(info, discussionId);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "댓글 페이지 조회 API", notes = SwaggerNote.CHARACTER_DISCUSSION_READL_ALL_NOTE)
    @GetMapping("/{characterId}/discussions")
    public ResponseEntity<PageDto<CharacterDiscussionResponse>> findAll(
            @PathVariable Long characterId,
            @PageableDefault(size = 20, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthUser AuthInfo info
    ) {
        var response = characterDiscussionFacade.findAll(info, pageable, characterId);
        return PageDto.ok(response);
    }

    @ApiOperation(value = "댓글 수정 API", notes = SwaggerNote.CHARACTER_DISCUSSION_UPDATE_NOTE)
    @PatchMapping("/discussions/{discussionId}")
    public ResponseEntity<CharacterDiscussionResponse> update(
            @PathVariable Long discussionId,
            @Validated @RequestBody CharacterDiscussionRequest request,
            @AuthUser AuthInfo info
    ) {
        var response = characterDiscussionFacade.update(info, discussionId, request);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "댓글 삭제 API", notes = SwaggerNote.CHARACTER_DISCUSSION_DELETE_NOTE)
    @DeleteMapping("/discussions/{discussionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long discussionId,
            @AuthUser AuthInfo info
    ) {
        characterDiscussionService.delete(discussionId, info.userId());
        return ResponseDto.noContent();
    }

    @ApiOperation(value = "댓글 좋아요", notes = SwaggerNote.CHARACTER_DISCUSSION_LIKE_CREATE_NOTE)
    @PutMapping("/discussions/{discussionId}/likes")
    public ResponseEntity<Void> create(
            @PathVariable Long discussionId,
            @AuthUser AuthInfo info
    ) {
        characterDiscussionLikeFacade.saveOrUpdate(info.userId(), discussionId);
        return ResponseDto.noContent();
    }
}
