package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.vote.facade.VoteFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "투표하기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/community/vote")
public class VoteController {
    private final VoteFacade voteFacade;

    @PatchMapping("/{voteSubjectId}")
    public ResponseEntity<Void> create(
            @PathVariable Long voteSubjectId,
            @AuthUser AuthInfo info
    ) {
        voteFacade.update(voteSubjectId, info.userId());
        return ResponseDto.noContent();
    }
}
