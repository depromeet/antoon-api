package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterHistory;
import kr.co.antoon.character.infrastructure.CharacterHistoryRepository;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.domain.vo.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CharacterHistoryServiceTest {
    @Mock
    private CharacterHistoryRepository characterHistoryRepository;

    @InjectMocks
    private CharacterHistoryService characterHistoryService;

    private final Long CHARACTER_ID = 1L;
    private final Long USER_ID = 1L;

    @Test
    public void 인물_관계_탑승하기() {
        // given
        var expected = new CharacterHistory(
                CHARACTER_ID,
                USER_ID
        );

        Mockito.when(characterHistoryRepository.save(any()))
                .thenReturn(expected);
        // when
        var actual = characterHistoryService.save(CHARACTER_ID, USER_ID);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 인물_관계_탑승한_상태() {
        // given
        CharacterHistory subjectRecommendation = new CharacterHistory(
                CHARACTER_ID,
                USER_ID
        );
        AuthInfo mockInfo = new AuthInfo(USER_ID, List.of(Role.USER));

        Mockito.when(characterHistoryRepository.findByCharacterIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(subjectRecommendation));
        // when
        var actual = characterHistoryService.isUserJoin(CHARACTER_ID, mockInfo);
        // then
        assertEquals(true, actual);
    }

    @Test
    public void 인물_관계_탑승안한_상태() {
        // given
        AuthInfo mockInfo = new AuthInfo(USER_ID, List.of(Role.USER));
        Mockito.when(characterHistoryRepository.findByCharacterIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());
        // when
        var actual = characterHistoryService.isUserJoin(CHARACTER_ID, mockInfo);
        // then
        assertEquals(false, actual);
    }
}
