package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.domain.WebtoonWriter;
import kr.co.antoon.webtoon.infrastructure.WebtoonWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        List<WebtoonWriter> webtoonWriter =  webtoonWriterRepository.findByWebtoonId(webtoonId);
        ArrayList<String> writer = new ArrayList<>();
        for(WebtoonWriter w: webtoonWriter) {
            writer.add(w.getName());
        }
        return writer;
    }
}