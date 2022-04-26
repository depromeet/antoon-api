package kr.co.antoon.like.presentation;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.like.application.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "종목토론방 좋아요 API")
@RestController
@RequestMapping("/api/v1/webtoons")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @ApiOperation(value = "댓글 좋아요")
    @PostMapping("/{discussionId}/likes")
    public ResponseEntity<Void> create(@PathVariable Long discussionId) {
        Long userId = 1L; // TODO Auth로 Id 받아야 합니다
        likeService.saveOrUpdate(userId, discussionId);
        return ResponseDto.noContent();
    }
}
