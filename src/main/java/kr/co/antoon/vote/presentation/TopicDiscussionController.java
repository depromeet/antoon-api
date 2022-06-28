package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.PageDto;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.vote.dto.request.TopicDiscussionCreateRequest;
import kr.co.antoon.vote.dto.request.TopicDiscussionUpdateRequest;
import kr.co.antoon.vote.dto.response.TopicDiscussionResponse;
import kr.co.antoon.vote.facade.TopicFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "커뮤니티 투표 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicDiscussionController {
    private final TopicFacade topicFacade;

    @ApiOperation(value = "토픽 상세 페이지 댓글 달기 API")
    @PostMapping("/{topicId}/discussions")
    public ResponseEntity<TopicDiscussionResponse> create(
            @PathVariable Long topicId,
            @Validated @RequestBody TopicDiscussionCreateRequest request,
            @AuthUser AuthInfo info
    ) {
        var response = topicFacade.createDiscussions(info.userId(), topicId, request);
        return ResponseDto.created(response);
    }

    @ApiOperation(value = "토픽 상세 페이지 댓글 조회")
    @GetMapping("/{topicId}/discussions")
    public ResponseEntity<PageDto<TopicDiscussionResponse>> findAll(
            @PathVariable Long topicId,
            @PageableDefault(size = 20, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthUser AuthInfo info
    ) {
        var response = topicFacade.findAllDiscussions(info, pageable, topicId);
        return PageDto.ok(response);
    }

    @ApiOperation(value = "토픽 상세 페이지 댓글 수정")
    @PatchMapping("/discussions/{discussionId}")
    public ResponseEntity<TopicDiscussionResponse> update(
            @PathVariable Long discussionId,
            @Validated @RequestBody TopicDiscussionUpdateRequest request,
            @AuthUser AuthInfo info
    ) {
        var response = topicFacade.updateDiscussions(info.userId(), discussionId, request);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "토픽 상세 페이지 댓글 삭제")
    @DeleteMapping("/discussions/{discussionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long discussionId,
            @AuthUser AuthInfo info
    ) {
        topicFacade.deleteDiscussions(discussionId, info.userId());
        return ResponseDto.noContent();
    }

    @ApiOperation(value = "토픽 상세 페이지 댓글 좋아요")
    @PutMapping("/discussions/{discussionId}/likes")
    public ResponseEntity<Void> create(
            @PathVariable Long discussionId,
            @AuthUser AuthInfo info
    ) {
        topicFacade.saveOrUpdateLikes(info.userId(), discussionId);
        return ResponseDto.noContent();
    }
}
