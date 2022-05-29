package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import kr.co.antoon.webtoon.infrastructure.WebtoonPublishDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebtoonPublishDayService {
    private final WebtoonPublishDayRepository webtoonPublishDayRepository;

    @Transactional
    public void saveAll(List<WebtoonPublishDay> webtoonPublishDays) {
        webtoonPublishDayRepository.saveAll(webtoonPublishDays);
    }

    @Transactional(readOnly = true)
    public boolean existsByWebtoonIdAndDay(Long webtoonId, String day) {
        return webtoonPublishDayRepository.existsByWebtoonIdAndDay(webtoonId, day);
    }
}