package kr.co.antoon.like.presentation;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.like.facade.LikeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "종목토론방 좋아요 API")
@RestController
@RequestMapping("/api/v1/webtoons")
@RequiredArgsConstructor
public class LikeController {

    private final LikeFacade likeFacade;

    @ApiOperation(value = "댓글 좋아요", notes = "*NOTE 추가 해주세요!!*")
    @PutMapping("/discussions/{discussionId}/likes")
    public ResponseEntity<Void> create(@PathVariable Long discussionId) {
        Long memberId = 1L; // TODO Auth로 Id 받아야 합니다
        likeFacade.saveOrUpdate(memberId, discussionId);
        return ResponseDto.noContent();
    }
}