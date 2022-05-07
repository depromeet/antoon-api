package kr.co.antoon.discussion.facade;

import kr.co.antoon.discussion.application.DiscussionLikeService;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionCreateResponse;
import kr.co.antoon.discussion.dto.response.DiscussionReadResponse;
import kr.co.antoon.discussion.dto.response.DiscussionUpdateResponse;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiscussionFacade {
    private final WebtoonService webtoonService;
    private final DiscussionLikeService discussionLikeService;
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
                discussion.getUserId(),
                discussion.getLikeCount(),
                false
        );
    }

    @Transactional
    public DiscussionReadResponse findById(Long memberId, Long discussionId) {
        var isUserLike = discussionLikeService.isUserLike(memberId, discussionId);
        var discussion = discussionService.findById(discussionId);
        return new DiscussionReadResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getUserId(),
                discussion.getLikeCount(),
                isUserLike
        );
    }

    @Transactional(readOnly = true)
    public Page<DiscussionReadResponse> findAll(Long memberId, Pageable pageable) {
        return discussionService.findAll(pageable)
                .map(discussion -> new DiscussionReadResponse(
                        discussion.getId(),
                        discussion.getContent(),
                        discussion.getUserId(),
                        discussion.getLikeCount(),
                        discussionLikeService.isUserLike(memberId, discussion.getId())
                ));
    }

    @Transactional
    public DiscussionUpdateResponse update(Long memberId, Long discussionId, DiscussionUpdateRequest request) {
        var isUserLike = discussionLikeService.isUserLike(memberId, discussionId);
        var discussion = discussionService.update(discussionId, request);
        return new DiscussionUpdateResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getUserId(),
                discussion.getLikeCount(),
                isUserLike
        );
    }
}