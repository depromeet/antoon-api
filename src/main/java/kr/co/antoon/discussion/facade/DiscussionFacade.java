package kr.co.antoon.discussion.facade;

import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionCreateResponse;
import kr.co.antoon.discussion.dto.response.DiscussionReadResponse;
import kr.co.antoon.discussion.dto.response.DiscussionUpdateResponse;
import kr.co.antoon.like.application.LikeService;
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
    private final LikeService likeService;
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
                discussion.getMemberId(),
                discussion.getLikeCount(),
                false
        );
    }

    @Transactional
    public DiscussionReadResponse findById(Long memberId, Long discussionId) {
        Boolean isUserLike = likeService.isUserLike(memberId, discussionId);
        Discussion discussion = discussionService.findById(discussionId);
        return new DiscussionReadResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId(),
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
                        discussion.getMemberId(),
                        discussion.getLikeCount(),
                        likeService.isUserLike(memberId, discussion.getId())
                ));
    }

    @Transactional
    public DiscussionUpdateResponse update(Long memberId, Long discussionId, DiscussionUpdateRequest request) {
        Boolean isUserLike = likeService.isUserLike(memberId, discussionId);
        Discussion discussion = discussionService.update(discussionId, request);
        return new DiscussionUpdateResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId(),
                discussion.getLikeCount(),
                isUserLike
        );
    }
}