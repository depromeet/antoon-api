package kr.co.antoon.discussion.presentation;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.discussion.facade.DiscussionLikeFacade;
import kr.co.antoon.oauth.config.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "종목토론방 좋아요 API")
@RestController
@RequestMapping(value = "/api/v1/webtoons")
@RequiredArgsConstructor
public class DiscussionLikeController {
    private final DiscussionLikeFacade discussionLikeFacade;

    @ApiOperation(value = "댓글 좋아요", notes = "*NOTE 추가 해주세요!!*")
    @PutMapping("/discussions/{discussionId}/likes")
    public ResponseEntity<Void> create(@PathVariable Long discussionId, @AuthUser Long memberId) {
        discussionLikeFacade.saveOrUpdate(memberId, discussionId);
        return ResponseDto.noContent();
    }
}