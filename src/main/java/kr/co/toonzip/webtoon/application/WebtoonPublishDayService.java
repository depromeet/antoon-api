package kr.co.toonzip.webtoon.application;

import kr.co.toonzip.webtoon.domain.WebtoonPublishDay;
import kr.co.toonzip.webtoon.infrastructure.WebtoonPublishDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebtoonPublishDayService {
    private final WebtoonPublishDayRepository webtoonPublishDayRepository;

    @Transactional
    public void save(WebtoonPublishDay webtoonPublishDay) {
        webtoonPublishDayRepository.save(webtoonPublishDay);
    }
}