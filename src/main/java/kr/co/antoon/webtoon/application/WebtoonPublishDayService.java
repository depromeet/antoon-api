package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import kr.co.antoon.webtoon.infrastructure.WebtoonPublishDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebtoonPublishDayService {
    private final WebtoonPublishDayRepository webtoonPublishDayRepository;

    @Transactional
    public void save(String day, Long webtoonId) {
        var webtoonPublishDay = new WebtoonPublishDay(day, webtoonId);
        webtoonPublishDayRepository.save(webtoonPublishDay);
    }
}