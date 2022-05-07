package kr.co.antoon.cruiser.facade;

import kr.co.antoon.cruiser.dto.slack.SlackCruiserResponse;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CruiserFacade {
    private final UserService userService;
    private final DiscussionService discussionService;

    public String countingData() {
        long userCount = userService.count();
        long discussionCount = discussionService.count();

        return SlackCruiserResponse.count(userCount, discussionCount);
    }
}