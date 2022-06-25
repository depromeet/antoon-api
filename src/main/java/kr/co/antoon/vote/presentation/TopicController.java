package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.PageDto;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.vote.domain.vo.SortType;
import kr.co.antoon.vote.dto.request.TopicDiscussionCreateRequest;
import kr.co.antoon.vote.dto.request.TopicDiscussionUpdateRequest;
import kr.co.antoon.vote.dto.response.TopicAllResponse;
import kr.co.antoon.vote.dto.response.TopicChoicesResponse;
import kr.co.antoon.vote.dto.response.TopicDiscussionResponse;
import kr.co.antoon.vote.dto.response.TopicResponse;
import kr.co.antoon.vote.facade.TopicFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "커뮤니티 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicController {
    private final TopicFacade topicFacade;

    @ApiOperation(value = "개미들의 선택 목록 조회")
    @GetMapping("/choices")
    public ResponseEntity<TopicChoicesResponse> getChoiceTopics() {
        var response = topicFacade.getChoiceTopics();
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "모든 토픽 목록 조회")
    @GetMapping("/{sortType}")
    public ResponseEntity<TopicAllResponse> getTopics(
        @RequestParam("sortType") SortType sortType
    ) {
        var response = topicFacade.findAll(sortType);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "토픽 상세 페이지 조회")
    @GetMapping("/detail/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(
            @PathVariable("topicId") Long topicId
    ) {
        var response = topicFacade.findTopicById(topicId);
        return ResponseDto.ok(response);
    }

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
}
