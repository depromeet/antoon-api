package kr.co.antoon.webtoon.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.webtoon.converter.WebtoonConverter;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.dto.WebtoonDto;
import kr.co.antoon.webtoon.dto.query.WebtoonCharacterNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonDayNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreBannerNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreNativeDto;
import kr.co.antoon.webtoon.dto.response.WebtoonAllResponse;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        if (!webtoonRepository.existsById(id)) {
            throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public WebtoonAllResponse searchAll() {
        var webtoons = findAll()
                .parallelStream()
                .map(WebtoonAllResponse.WebtoonDetail::new)
                .collect(Collectors.toList());

        return new WebtoonAllResponse(webtoons);
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findAllByStatus(ActiveStatus status) {
        return webtoonRepository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    public Webtoon findById(Long id) {
        return webtoonRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR));
    }

    @Transactional(readOnly = true)
    public long countByStatus(ActiveStatus status) {
        return webtoonRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findWebtoonByGenreAndStatus(String genre, ActiveStatus status) {
        return webtoonRepository.findByGenreAndStatus(genre, status);
    }

    @Transactional(readOnly = true)
    public WebtoonDto findDetailWebtoon(Long webtoonId) {
        var end = LocalDateTime.now();
        var start = end.minusHours(1);
        var webtoon = webtoonRepository.findOneByWebtoonId(webtoonId, start.toString(), end.toString());

        if (webtoon.size() == 0) {
            throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR);
        }
        return WebtoonConverter.toWebtoonDto(webtoon);
    }

    @Transactional(readOnly = true)
    public Page<WebtoonDayNativeDto> findAllByDay(String day, Pageable pageable) {
        return webtoonRepository.findAllByDay(day, pageable);
    }

    @Transactional(readOnly = true)
    public List<WebtoonGenreBannerNativeDto> findGenre(LocalDateTime start, LocalDateTime end, GenreCategory category) {
        return webtoonRepository.findGenre(start.toString(), end.toString(), category.name());
    }

    @Transactional(readOnly = true)
    public Page<WebtoonGenreNativeDto> findAllByGenre(LocalDateTime start, LocalDateTime end, GenreCategory category, Pageable pageable) {
        return webtoonRepository.findAllByGenre(start.toString(), end.toString(), category.name(), pageable);
    }
  
    @Transactional(readOnly = true)
    public WebtoonCharacterNativeDto findPreviewWebtoon(Long webtoonId) {
        var end = LocalDateTime.now();
        var start = end.minusHours(1);
        return webtoonRepository.findPreviewByWebtoonId(webtoonId, start.toString(), end.toString());
    }
}
