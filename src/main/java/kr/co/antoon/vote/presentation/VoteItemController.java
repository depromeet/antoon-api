package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.vote.dto.response.VoteItemResponse;
import kr.co.antoon.vote.facade.VoteItemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "투표 상세 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/vote-items")
public class VoteItemController {
    private final VoteItemFacade voteItemFacade;

    @ApiOperation(value = "투표 상세 페이지 AB 투표 조회")
    @GetMapping("/{voteItemId}")
    public ResponseEntity<VoteItemResponse> getVoteItemById(
            @PathVariable("voteItemId") Long voteItemId
    ) {
        var response = voteItemFacade.findVoteItemById(voteItemId);
        return ResponseDto.ok(response);
    }
}
