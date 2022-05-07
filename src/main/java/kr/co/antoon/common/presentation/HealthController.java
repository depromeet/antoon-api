package kr.co.antoon.common.presentation;

import kr.co.antoon.graph.facade.GraphScoreFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {
    private final GraphScoreFacade graphScoreFacade;
    @GetMapping
    public String health() {
        graphScoreFacade.snapshot();
        return "Health Good!";
    }
}