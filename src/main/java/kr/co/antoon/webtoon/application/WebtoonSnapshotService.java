package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.infrastructure.WebtoonSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebtoonSnapshotService {
    private final WebtoonSnapshotRepository webtoonSnapshotRepository;

    @Transactional
    public void save(Double score, Long webtoonId) {
        var webtoonSnapshot = new WebtoonSnapshot(score, webtoonId);
        webtoonSnapshotRepository.save(webtoonSnapshot);
    }

    @Transactional(readOnly = true)
    public List<WebtoonSnapshot> findAllBySnapshopTime(LocalDate time) {
        return webtoonSnapshotRepository.findAllBySnapshotTime(time);
    }
}