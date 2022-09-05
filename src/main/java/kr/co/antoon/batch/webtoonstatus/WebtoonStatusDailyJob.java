package kr.co.antoon.batch.webtoonstatus;

import kr.co.antoon.webtoon.facade.WebtoonStatusFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebtoonStatusDailyJob {
    private final WebtoonStatusFacade webtoonStatusFacade;

    public void run() {
        webtoonStatusFacade.changeAllStatus();
    }
}
