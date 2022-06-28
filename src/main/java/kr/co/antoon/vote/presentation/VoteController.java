package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.vote.facade.VoteFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "투표하기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vote")
public class VoteController {
    private final VoteFacade voteFacade;

    @PostMapping("/{candidateId}")
    public ResponseEntity<Void> create(
            @PathVariable Long candidateId,
            @AuthUser AuthInfo info
    ) {
        try {
            voteFacade.create(candidateId, info.userId());
            return ResponseDto.noContent();
        } catch (AlreadyExistsException e) {
            return ResponseDto.conflict();
        }
    }
}
