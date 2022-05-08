package kr.co.antoon.webtoon.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.response.WebtoonAllResponse;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public void existsById(Long id) {
        if (webtoonRepository.existsById(id)) {
            throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public WebtoonAllResponse searchAll() {
        return new WebtoonAllResponse(
                findAll().stream()
                        .map(WebtoonAllResponse.WebtoonDetail::new)
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findAllByStatus(ActiveStatus status) {
        return webtoonRepository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    public Webtoon findById(Long id) {
        return webtoonRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findWebtoonByGenreAndStatus(String genre, ActiveStatus status) {
        return webtoonRepository.findByGenreAndStatus(genre, status);
    }
}