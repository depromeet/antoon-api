package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;

    @Transactional
    public Long save(Webtoon webtoon) {
        return webtoonRepository.save(webtoon).getId();
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findAll() {
        return webtoonRepository.findAll();
    }
}