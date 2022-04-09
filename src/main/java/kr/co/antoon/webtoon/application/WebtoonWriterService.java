package kr.co.antoon.webtoon.application;

import kr.co.antoon.webtoon.infrastructure.WebtoonWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebtoonWriterService {
    private final WebtoonWriterRepository webtoonWriterRepository;

    @Transactional
    public void saveAll(){

    }
}