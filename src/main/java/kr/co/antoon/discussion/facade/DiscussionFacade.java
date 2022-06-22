package kr.co.antoon.discussion.facade;

import kr.co.antoon.discussion.application.DiscussionLikeService;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.converter.DiscussionConverter;
import kr.co.antoon.discussion.dto.request.DiscussionCreateRequest;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
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
    private final UserService userService;

    @Transactional
    public DiscussionResponse register(Long userId, Long webtoonId, DiscussionCreateRequest request) {
        webtoonService.existsById(webtoonId);

        var discussion = discussionService.save(
                userId,
                webtoonId,
                request.content()
        );

        var user = userService.findById(discussion.getUserId());

        return new DiscussionResponse(
                webtoonId,
                discussion,
                user,
                false,
                discussionService.getTime(discussion.getCreatedAt())
        );
    }

    @Transactional
    public DiscussionResponse findById(AuthInfo info, Long discussionId) {
        var discussion = discussionService.findById(discussionId);
        var user = userService.findById(discussion.getUserId());
        if (info == null) {
            return new DiscussionResponse(
                    discussion.getWebtoonId(),
                    discussion,
                    user,
                    false,
                    discussionService.getTime(discussion.getCreatedAt())
            );
        }
        var isUserLike = discussionLikeService.isUserLike(info.userId(), discussionId);
        return new DiscussionResponse(
                discussion.getWebtoonId(),
                discussion,
                user,
                isUserLike,
                discussionService.getTime(discussion.getCreatedAt())
        );
    }

    @Transactional(readOnly = true)
    public Page<DiscussionResponse> findAll(AuthInfo info, Pageable pageable, Long webtoonId) {
        if (info == null) {
            return discussionService.findByWebtoonId(webtoonId, pageable)
                    .map(discussion -> {
                        var user = userService.findById(discussion.getUserId());
                        return new DiscussionResponse(
                                webtoonId,
                                discussion,
                                user,
                                false,
                                discussionService.getTime(discussion.getCreatedAt())
                        );
                    });
        }
        return discussionService.findByWebtoonId(webtoonId, pageable)
                .map(discussion -> {
                    var user = userService.findById(discussion.getUserId());
                    var userLike = discussionLikeService.isUserLike(info.userId(), discussion.getId());
                    return new DiscussionResponse(
                            webtoonId,
                            discussion,
                            user,
                            userLike,
                            discussionService.getTime(discussion.getCreatedAt())
                    );
                });
    }

    @Transactional
    public DiscussionResponse update(Long userId, Long discussionId, DiscussionUpdateRequest request) {
        var discussion = discussionService.update(discussionId, userId, request);
        var user = userService.findById(discussion.getUserId());
        var isUserLike = discussionLikeService.isUserLike(userId, discussionId);

        return DiscussionConverter.toDiscussionResponse(
                discussion,
                user,
                isUserLike,
                discussionService.getTime(discussion.getCreatedAt())
        );
    }
}
