package kr.co.antoon.vote.presentation;

import io.swagger.annotations.Api;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.vote.facade.VoteFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        voteFacade.create(candidateId, info.userId());
        return ResponseDto.noContent();
    }
}
