package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    @Transactional
    public Discussion save(Long memberId, Long webtoonId, String content) {
        return discussionRepository.save(
                Discussion.builder()
                        .memberId(memberId)
                        .webtoonId(webtoonId)
                        .content(content)
                        .build()
        );
    }
}