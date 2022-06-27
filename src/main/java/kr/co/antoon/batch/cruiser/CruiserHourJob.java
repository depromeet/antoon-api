package kr.co.antoon.batch.cruiser;

import kr.co.antoon.cruiser.domain.Cruiser;
import kr.co.antoon.cruiser.facade.CruiserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CruiserHourJob {
    private final Cruiser cruiser;
    private final CruiserFacade cruiserFacade;

    public void run() {
        var message = cruiserFacade.statistics();
        cruiser.send(message);

        var topRanks = cruiserFacade.topRanks();
        cruiser.send(topRanks);
    }
}
