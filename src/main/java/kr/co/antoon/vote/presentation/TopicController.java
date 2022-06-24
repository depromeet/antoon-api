package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.vote.domain.vo.SortType;
import kr.co.antoon.vote.dto.response.TopicAllResponse;
import kr.co.antoon.vote.dto.response.TopicChoicesResponse;
import kr.co.antoon.vote.dto.response.TopicResponse;
import kr.co.antoon.vote.facade.TopicFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "토픽 상세 페이지 Topic 조회")
    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(
            @PathVariable("topicId") Long topicId
    ) {
        var response = topicFacade.findTopicById(topicId);
        return ResponseDto.ok(response);
    }
}
