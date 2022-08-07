package kr.co.antoon.vote.application;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.Vote;
import kr.co.antoon.vote.domain.vo.TopicCategory;
import kr.co.antoon.vote.infrastructure.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;

    @Test
    public void 투표_생성하기() {
        // given
        Topic mockTopic = Topic.builder()
                .title("연애혁명")
                .topicCategory(TopicCategory.AB)
                .topicVoteStatus(false)
                .joinCount(0)
                .tags("#연애혁명")
                .build();
    }
}
