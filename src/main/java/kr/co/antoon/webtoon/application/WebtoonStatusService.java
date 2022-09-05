package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonStatus;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import kr.co.antoon.webtoon.infrastructure.WebtoonStatusRepository;
import kr.co.antoon.webtoon.converter.WebtoonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebtoonStatusService {
    private final WebtoonStatusRepository webtoonStatusRepository;

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndWebtoonIdAndStatus(Long userId, Long webtoonId, WebtoonStatusType status) {
        return webtoonStatusRepository.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, status);
    }

    @Transactional
    public void save(Long userId, Long webtoonId, WebtoonStatusType status) {
        var webtoonStatus = new WebtoonStatus(userId, webtoonId, status);

        webtoonStatusRepository.save(webtoonStatus);
    }

    @Transactional(readOnly = true)
    public List<WebtoonStatus> findAllByStatus(WebtoonStatusType status) {
        return webtoonStatusRepository.findAll()
                .stream()
                .filter(ws -> ws.getStatus().equals(status))
                .toList();
    }
}
