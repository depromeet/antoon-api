package kr.co.antoon.vote.application;

import kr.co.antoon.vote.domain.TopicDiscussionLike;
import kr.co.antoon.vote.infrastructure.TopicDiscussionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicDiscussionLikeService {
    private final TopicDiscussionLikeRepository likeRepository;

    @Transactional
    public TopicDiscussionLike SaveOrUpdate(Long userId, Long discussionId) {
        return likeRepository.save(
                likeRepository.findByUserIdAndDiscussionId(userId, discussionId)
                        .map(TopicDiscussionLike::update)
                        .orElse(TopicDiscussionLike.builder()
                                .userId(userId)
                                .discussionId(discussionId)
                                .build()
                        )
        );
    }

    @Transactional(readOnly = true)
    public Boolean isUserLike(Long userId, Long discussionId) {
        var like = likeRepository.findByUserIdAndDiscussionId(userId, discussionId);
        if (like.isPresent()) {
            return like.get().getStatus();
        }
        return false;
    }
}
