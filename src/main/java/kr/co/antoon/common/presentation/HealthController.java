package kr.co.antoon.common.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "Health Good!";
    }
}