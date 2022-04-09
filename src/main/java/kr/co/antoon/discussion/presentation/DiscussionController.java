package kr.co.antoon.discussion.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionCreateResponse;
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

    @ApiOperation(value = "종목토론방 댓글 달기 API", notes = SwaggerNote.DISCUSSION_CREATE_NOTE)
    @PostMapping("/webtoons/{webtoonId}/discussions")
    public ResponseEntity<DiscussionCreateResponse> create(
            @PathVariable Long webtoonId,
            @RequestBody DiscussionCreateRequest request
    ) {
        Long memberId = 1L;
        return ResponseDto.ok(discussionFacade.register(memberId, webtoonId, request));
    }
}