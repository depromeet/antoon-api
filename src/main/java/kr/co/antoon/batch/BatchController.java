package kr.co.antoon.batch;

import io.swagger.annotations.Api;
import kr.co.antoon.batch.crawling.CrawlingDailyJob;
import kr.co.antoon.batch.cruiser.CruiserHourJob;
import kr.co.antoon.batch.graph.GraphHourJob;
import kr.co.antoon.batch.recommendation.RecommendationDailyJob;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

/**
 * Temporal controller for running batch jobs.
 * Alternative of batch server, this controller should be deprecated.
 */
@Api(tags = "Batch")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/batch", produces = APPLICATION_JSON_UTF_8)
public class BatchController {
    private final CrawlingDailyJob crawlingDailyJob;
    private final CruiserHourJob cruiserHourJob;
    private final GraphHourJob graphHourJob;
    private final RecommendationDailyJob recommendationDailyJob;

    @Value("${batch.key}")
    private String batchKey;

    @PatchMapping("/crawling/naver")
    public void runCrawlingNaverJob(
            @RequestBody BatchRequest request) {
        if (batchKey.equals(request.batchKey())) {
            crawlingDailyJob.run(Platform.NAVER);
        }
    }

    @PatchMapping("/crawling/kakao")
    public void runCrawlingKakaoJob(
            @RequestBody BatchRequest request) {
        if (batchKey.equals(request.batchKey())) {
            crawlingDailyJob.run(Platform.KAKAO);
        }
    }

    @PatchMapping("/cruiser")
    public void runCruiserJob(
            @RequestBody BatchRequest request) {
        if (batchKey.equals(request.batchKey())) {
            cruiserHourJob.run();
        }
    }

    @PatchMapping("/graph")
    public void runGraphJob(
            @RequestBody BatchRequest request) {
        if (batchKey.equals(request.batchKey())) {
            graphHourJob.run();
        }
    }

    @PatchMapping("/recommendation")
    public void runDailyJobLeavedStatusJob(
            @RequestBody BatchRequest request) {
        if (batchKey.equals(request.batchKey())) {
            recommendationDailyJob.run();
        }
    }
}
