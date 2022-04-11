package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionReadResponse;
import kr.co.antoon.discussion.dto.response.DiscussionUpdateResponse;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @Transactional(readOnly = true)
    public DiscussionReadResponse findById(Long id) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR, HttpStatus.NOT_FOUND));

        return new DiscussionReadResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId()
        );
    }

    @Transactional(readOnly = true)
    public Page<DiscussionReadResponse> findAll(Pageable pageable) {
        return discussionRepository.findAll(pageable)
                .map(discussion -> new DiscussionReadResponse(
                        discussion.getId(),
                        discussion.getContent(),
                        discussion.getMemberId()
                ));
    }

    @Transactional
    public DiscussionUpdateResponse update(Long id, DiscussionUpdateRequest request) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR, HttpStatus.NOT_FOUND));

        discussion.update(request.content());

        return new DiscussionUpdateResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId()
        );
    }
}