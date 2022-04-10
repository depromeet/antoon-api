package kr.co.antoon.discussion.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionCreateResponse;
import kr.co.antoon.discussion.dto.response.DiscussionReadResponse;
import kr.co.antoon.discussion.facade.DiscussionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "종목토론방 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionFacade discussionFacade;
    private final DiscussionService discussionService;

    @ApiOperation(value = "종목토론방 댓글 달기 API", notes = SwaggerNote.DISCUSSION_CREATE_NOTE)
    @PostMapping("/webtoons/{webtoonId}/discussions")
    public ResponseEntity<DiscussionCreateResponse> create(
            @PathVariable Long webtoonId,
            @RequestBody DiscussionCreateRequest request
    ) {
        Long memberId = 1L; // TODO Auth로 Id 받아야 합니다
        return ResponseDto.created(discussionFacade.register(memberId, webtoonId, request));
    }

    @ApiOperation(value = "종목토론방 댓글 단건 조회", notes = "")
    @GetMapping("/discussions/{discussionId}")
    public ResponseEntity<DiscussionReadResponse> findOne(@PathVariable Long discussionId) {
        var response = discussionService.findById(discussionId);
        return ResponseDto.ok(response);
    }
}