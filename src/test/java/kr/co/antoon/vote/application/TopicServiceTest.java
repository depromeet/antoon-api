package kr.co.antoon.vote.application;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.TopicCategory;
import kr.co.antoon.vote.infrastructure.TopicRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @DisplayName("개미들의 선택 목록 조회")
    @Test
    public void find_all_choice_topics() {
        // given
        var expected = new ArrayList<Topic>();
        for (int i = 0; i < 8; i++) {
            expected.add(Topic.builder()
                    .title("연애혁명")
                    .topicCategory(TopicCategory.AB)
                    .joinCount(0)
                    .topicVoteStatus(false)
                    .tags("#연애혁명")
                    .build()
            );
        }
        Mockito.when(topicRepository.findTop8ByOrderByJoinCountDesc())
                .thenReturn(expected);
        // when
        List<Topic> actual = topicService.findAllChoiceTopics();
        // then
        assertEquals(expected, actual);
    }
    
    @DisplayName("토픽 상세 조회")
    @Test
    public void find_by_id() {
        // given
        Topic expected = Topic.builder()
                .title("삼국지여포전")
                .topicCategory(TopicCategory.AB)
                .joinCount(0)
                .topicVoteStatus(false)
                .tags("#삼국지")
                .build();

        Mockito.when(topicRepository.findById(anyLong()))
                .thenReturn(Optional.of(expected));
        // when
        Topic actual = topicService.findById(1L);
        // then
        assertEquals(expected, actual);
    }
}
