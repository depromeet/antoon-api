package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterImage;
import kr.co.antoon.character.domain.vo.CharacterImageType;
import kr.co.antoon.character.infrastructure.CharacterImageRepository;
import kr.co.antoon.error.exception.character.NotExistsCharacterImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CharacterImageService {
    private final CharacterImageRepository characterImageRepository;

    @Transactional(readOnly = true)
    public CharacterImage findByCharacterIdAndType(Long characterId, CharacterImageType type) {
        return characterImageRepository.findByCharacterIdAndType(characterId, type)
                .orElseThrow(NotExistsCharacterImageException::new);
    }

    @Transactional
    public CharacterImage save(String imageUrl, Long characterId, CharacterImageType type) {
        return characterImageRepository.save(CharacterImage.builder()
                .imageUrl(imageUrl)
                .type(type)
                .characterId(characterId)
                .build()
        );
    }
}
