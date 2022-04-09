package kr.co.antoon.discussion.facade;

import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionCreateResponse;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiscussionFacade {
    private final WebtoonService webtoonService;
    private final DiscussionService discussionService;

    @Transactional
    public DiscussionCreateResponse register(Long memberId, Long webtoonId, DiscussionCreateRequest request) {
        webtoonService.existsById(webtoonId);

        var discussion = discussionService.save(
                memberId,
                webtoonId,
                request.content()
        );

        return new DiscussionCreateResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId()
        );
    }
}