package kr.co.antoon.webtoon.facade;

import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.error.exception.webtoon.AlreadyJoinedException;
import kr.co.antoon.error.exception.webtoon.AlreadyLeavedException;
import kr.co.antoon.webtoon.application.WebtoonStatusCountService;
import kr.co.antoon.webtoon.application.WebtoonStatusService;
import kr.co.antoon.webtoon.domain.WebtoonStatusCount;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import kr.co.antoon.webtoon.dto.response.WebtoonStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WebtoonStatusFacade {
    private final WebtoonStatusService webtoonStatusService;
    private final WebtoonStatusCountService webtoonStatusCountService;
    private final AntCoinService antCoinService;

    @Transactional
    public WebtoonStatusResponse saveOrUpdate(WebtoonStatusType status, Long userId, Long webtoonId) {
        statusCheck(userId, webtoonId);

        WebtoonStatusCount webtoonStatusCount = webtoonStatusCountService.findByWebtoonId(webtoonId)
                .orElseGet(() -> webtoonStatusCountService.save(webtoonId));
        webtoonStatusCount.updateCount(status);

        status = WebtoonStatusType.of(status);
        webtoonStatusService.save(webtoonId, userId, status);
        WebtoonStatusResponse response = new WebtoonStatusResponse(webtoonStatusCount, status);

        return antCoinService.joinWebtoon(userId, webtoonId, response, status);
    }

    @Transactional(readOnly = true)
    public void statusCheck(Long userId, Long webtoonId) {
        if (webtoonStatusService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, WebtoonStatusType.LEAVED)) {
            throw new AlreadyLeavedException();
        }
        if (webtoonStatusService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, WebtoonStatusType.JOINED)) {
            throw new AlreadyJoinedException();
        }
    }

    @Transactional
    public void changeAllStatus() {
        webtoonStatusService.findAllByStatus(WebtoonStatusType.JOINED)
                .forEach(webtoonStatus -> {
                    webtoonStatus.updateStatus(WebtoonStatusType.NONE);
                    webtoonStatusCountService.findByWebtoonId(webtoonStatus.getWebtoonId())
                            .ifPresent(ws -> ws.updateCount(WebtoonStatusType.JOINED));
                });

        webtoonStatusService.findAllByStatus(WebtoonStatusType.LEAVED)
                .forEach(webtoonStatus -> {
                    webtoonStatus.updateStatus(WebtoonStatusType.NONE);
                    webtoonStatusCountService.findByWebtoonId(webtoonStatus.getWebtoonId())
                            .ifPresent(ws -> ws.updateCount(WebtoonStatusType.LEAVED));
                });
    }
}
