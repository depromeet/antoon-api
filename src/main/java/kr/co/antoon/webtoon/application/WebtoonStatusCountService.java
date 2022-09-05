package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonStatusCount;
import kr.co.antoon.webtoon.infrastructure.WebtoonStatusCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebtoonStatusCountService {
    private final WebtoonStatusCountRepository webtoonStatusCountRepository;

    @Transactional(readOnly = true)
    public Optional<WebtoonStatusCount> findByWebtoonId(Long webtoonId) {
        return webtoonStatusCountRepository.findByWebtoonId(webtoonId);
    }

    @Transactional
    public WebtoonStatusCount save(Long webtoonId) {
        return webtoonStatusCountRepository.save(new WebtoonStatusCount(webtoonId));
    }

    @Transactional(readOnly = true)
    public List<WebtoonStatusCount> findAll() {
        return webtoonStatusCountRepository.findAll();
    }
}
