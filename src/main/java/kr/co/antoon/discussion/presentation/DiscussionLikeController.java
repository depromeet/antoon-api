package kr.co.antoon.discussion.presentation;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.discussion.facade.DiscussionLikeFacade;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.Utility.APPLICATION_JSON_UTF_8;

@Api(tags = "종목토론방 좋아요 API")
@RestController
@RequestMapping(value = "/api/v1/webtoons", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class DiscussionLikeController {
    private final DiscussionLikeFacade discussionLikeFacade;

    @ApiOperation(value = "댓글 좋아요", notes = SwaggerNote.DISCUSSION_LIKE_CREATE_NOTE)
    @PutMapping("/discussions/{discussionId}/likes")
    public ResponseEntity<Void> create(@PathVariable Long discussionId, @AuthUser AuthInfo info) {
        discussionLikeFacade.saveOrUpdate(info.userId(), discussionId);
        return ResponseDto.noContent();
    }
}