package kr.co.antoon.webtoon.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.WebtoonDto;
import kr.co.antoon.webtoon.dto.response.WebtoonAllResponse;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                findAll().parallelStream()
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
    public long countByStatus(ActiveStatus status) {
        return webtoonRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Webtoon> findWebtoonByGenreAndStatus(String genre, ActiveStatus status) {
        return webtoonRepository.findByGenreAndStatus(genre, status);
    }

    @Transactional(readOnly = true)
    public WebtoonDto findDetailWebtoon(Long webtoonId) {
        var webtoon = webtoonRepository.findOneByWebtoonId(webtoonId);

        if (webtoon.size() == 0) {
            throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR);
        }

        List<WebtoonDto.GenreDto> genres = new ArrayList<>();
        List<WebtoonDto.PublishDayDto> days = new ArrayList<>();
        List<WebtoonDto.WriterDto> writers = new ArrayList<>();

        webtoon.forEach(dto -> {
            genres.add(new WebtoonDto.GenreDto(
                    dto.getWebtoonGenreId(),
                    dto.getGenreCategory(),
                    dto.getGenreCategory().getDescription()
            ));
            days.add(new WebtoonDto.PublishDayDto(
                    dto.getWebtoonPublishDayId(),
                    dto.getDay()
            ));
            writers.add(new WebtoonDto.WriterDto(
                    dto.getWebtoonWriterId(),
                    dto.getName()
            ));
        });


        return new WebtoonDto(
                webtoon.get(0).getWebtoonId(),
                webtoon.get(0).getTitle(),
                webtoon.get(0).getContent(),
                webtoon.get(0).getWebtoonUrl(),
                webtoon.get(0).getThumbnail(),
                webtoon.get(0).getPlatform(),
                webtoon.get(0).getPlatform().getDescription(),
                webtoon.get(0).getStatus(),
                webtoon.get(0).getStatus().getDescription(),
                genres,
                days,
                writers
        );
    }
}