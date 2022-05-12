package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.infrastructure.WebtoonSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebtoonSnapshotService {
    private final WebtoonSnapshotRepository webtoonSnapshotRepository;

    @Transactional
    public void saveAll(List<WebtoonSnapshot> webtoonSnapshots) {
        webtoonSnapshotRepository.saveAll(webtoonSnapshots);
    }

    @Transactional
    public void save(Double score, Long webtoonId) {
        var webtoonSnapshot = new WebtoonSnapshot(score, webtoonId);
        webtoonSnapshotRepository.save(webtoonSnapshot);
    }

    @Transactional(readOnly = true)
    public List<WebtoonSnapshot> findAllBySnapshopTime(LocalDate time) {
        return webtoonSnapshotRepository.findAllBySnapshotTime(time);
    }

    @Transactional(readOnly = true)
    public Optional<WebtoonSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoonId) {
        return webtoonSnapshotRepository.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);
    }

    @Transactional(readOnly = true)
    public long count() {
        return webtoonSnapshotRepository.count();
    }
}