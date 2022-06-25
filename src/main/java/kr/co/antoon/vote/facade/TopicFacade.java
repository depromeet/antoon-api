package kr.co.antoon.vote.facade;

import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.TopicDiscussionLikeService;
import kr.co.antoon.vote.application.TopicDiscussionService;
import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.converter.TopicDiscussionConverter;

import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.TopicDiscussion;
import kr.co.antoon.vote.domain.vo.SortType;
import kr.co.antoon.vote.dto.request.TopicDiscussionCreateRequest;
import kr.co.antoon.vote.dto.request.TopicDiscussionUpdateRequest;
import kr.co.antoon.vote.dto.response.TopicAllResponse;
import kr.co.antoon.vote.dto.response.TopicChoicesResponse;

import kr.co.antoon.vote.dto.response.TopicDiscussionResponse;
import kr.co.antoon.vote.dto.response.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

@Component
@RequiredArgsConstructor
public class TopicFacade {
    private final TopicService topicService;
    private final CandidateService candidateService;
    private final TopicDiscussionService topicDiscussionService;
    private final TopicDiscussionLikeService topicDiscussionLikeService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public TopicResponse findTopicById(Long topicId) {
        var topic = topicService.findById(topicId);
        var candidates = candidateService.findAllByTopicId(topicId);
        return new TopicResponse(topic, candidates);
    }

    @Transactional(readOnly = true)
    public TopicAllResponse findAll(SortType sortType) {
        var response = topicService.findAllTopics(sortType)
                .stream()
                .map(topic -> {
                    String[] thumbnails = candidateService.findAllByTopicId(topic.getId())
                            .stream()
                            .map(Candidate::getImageUrl)
                            .toArray(String[]::new);
                    return new TopicAllResponse.TopicResponse(
                            topic,
                            thumbnails
                    );
                }).toList();
        return new TopicAllResponse(response);
    }

    @Transactional(readOnly = true)
    public TopicChoicesResponse getChoiceTopics() {
        var responses = topicService.findAllChoiceTopics()
                .stream()
                .map(TopicChoicesResponse.TopicChoiceResponse::new)
                .toList();
        return new TopicChoicesResponse(responses);
    }

    @Transactional
    public TopicDiscussionResponse createDiscussions(Long userId, Long topicId, TopicDiscussionCreateRequest request) {
        topicService.existsById(topicId);
        TopicDiscussion topicDiscussion = topicDiscussionService.save(
                userId,
                topicId,
                request.content()
        );
        var user = userService.findById(topicDiscussion.getUserId());
        return new TopicDiscussionResponse(
                topicId,
                topicDiscussion,
                user,
                false,
                topicDiscussionService.getTime(topicDiscussion.getCreatedAt())
        );
    }

    @Transactional
    public TopicDiscussionResponse updateDiscussions(Long userId, Long discussionId, TopicDiscussionUpdateRequest request) {
        var topicDiscussion = topicDiscussionService.update(discussionId, userId, request);
        var user = userService.findById(topicDiscussion.getUserId());
        var isUserLike = topicDiscussionLikeService.isUserLike(userId, discussionId);

        return TopicDiscussionConverter.toTopicCommentResponse(
                topicDiscussion,
                user,
                isUserLike,
                topicDiscussionService.getTime(topicDiscussion.getCreatedAt())
        );
    }

    public void deleteDiscussions(Long discussionId, Long userId) {
        topicDiscussionService.delete(discussionId, userId);
    }

    @Transactional(readOnly = true)
    public Page<TopicDiscussionResponse> findAllDiscussions(AuthInfo info, Pageable pageable, Long topicId) {
        if (info == null) {
            return topicDiscussionService.findByTopicId(topicId, pageable)
                    .map(topicDiscussion -> {
                        var user = userService.findById(topicDiscussion.getUserId());
                        return new TopicDiscussionResponse(
                                topicId,
                                topicDiscussion,
                                user,
                                false,
                                topicDiscussionService.getTime(topicDiscussion.getCreatedAt())
                        );
                    });
        }
        return topicDiscussionService.findByTopicId(topicId, pageable)
                .map(topicDiscussion -> {
                    var user = userService.findById(topicDiscussion.getUserId());
                    var userLike = topicDiscussionLikeService.isUserLike(info.userId(), topicDiscussion.getId());
                    return new TopicDiscussionResponse(
                            topicId,
                            topicDiscussion,
                            user,
                            userLike,
                            topicDiscussionService.getTime(topicDiscussion.getCreatedAt())
                    );
                });
    }
}
