package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import kr.co.antoon.webtoon.infrastructure.WebtoonPublishDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonPublishDayService {
    private final WebtoonPublishDayRepository webtoonPublishDayRepository;

    @Transactional
    public void save(String day, Long webtoonId) {
        var webtoonPublishDay = new WebtoonPublishDay(day, webtoonId);
        webtoonPublishDayRepository.save(webtoonPublishDay);
    }

    @Transactional
    public List<String> findDaysByWebtoonId(Long webtoonId) {
        return webtoonPublishDayRepository.findAllByWebtoonId(webtoonId)
                .stream()
                .map(WebtoonPublishDay::getDay)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existsByWebtoonIdAndDay(Long webtoonId, String day) {
        return webtoonPublishDayRepository.existsByWebtoonIdAndDay(webtoonId, day);
    }
}