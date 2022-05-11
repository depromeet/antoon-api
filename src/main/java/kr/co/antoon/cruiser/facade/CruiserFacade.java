package kr.co.antoon.cruiser.facade;

import kr.co.antoon.cruiser.dto.slack.SlackCruiserResponse;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CruiserFacade {
    private final UserService userService;
    private final DiscussionService discussionService;
    private final WebtoonService webtoonService;

    @Transactional(readOnly = true)
    public String sendStatistics() {
        var userCount = userService.count();
        var discussionCount = discussionService.count();
        var webtoonCount = webtoonService.countByStatus(ActiveStatus.PUBLISH);

        return SlackCruiserResponse.dataStatistics(
                userCount,
                discussionCount,
                webtoonCount
        );
    }
}