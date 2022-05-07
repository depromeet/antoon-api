package kr.co.antoon.batch.graph;

import kr.co.antoon.graph.facade.GraphScoreFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphScoreHourJob {
    private final GraphScoreFacade graphScoreFacade;

    public void run() {
        graphScoreFacade.snapshot();
    }
}