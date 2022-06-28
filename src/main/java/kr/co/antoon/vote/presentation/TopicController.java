package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.PageDto;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.vote.domain.vo.SortType;
import kr.co.antoon.vote.dto.response.*;
import kr.co.antoon.vote.facade.TopicFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = "커뮤니티 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicController {
    private final TopicFacade topicFacade;

    @ApiOperation(value = "모든 토픽 목록 조회", notes = SwaggerNote.TOPIC_READ_ALL_NOTE)
    @GetMapping("/{sortType}")
    public ResponseEntity<PageDto<TopicPageResponse>> getTopics(
            @PathVariable("sortType") SortType sortType,
            @PageableDefault(size = 5, page = 0) Pageable pageable
    ) {
        var response = topicFacade.findAll(sortType, pageable);
        return PageDto.ok(response);
    }

    @ApiOperation(value = "개미들의 선택 목록 조회")
    @GetMapping("/choices")
    public ResponseEntity<TopicChoicesResponse> getChoiceTopics() {
        var response = topicFacade.getChoiceTopics();
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
}
