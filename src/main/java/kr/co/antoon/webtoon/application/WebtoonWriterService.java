package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonWriter;
import kr.co.antoon.webtoon.infrastructure.WebtoonWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonWriterService {
    private final WebtoonWriterRepository webtoonWriterRepository;

    @Transactional
    public void saveAll(List<WebtoonWriter> writers) {
        webtoonWriterRepository.saveAll(writers);
    }

    @Transactional(readOnly = true)
    public List<String> findNameByWebtoonId(Long webtoonId) {
      return webtoonWriterRepository.findByWebtoonId(webtoonId)
              .stream()
              .map(WebtoonWriter::getName)
              .collect(Collectors.toList());
    }
}