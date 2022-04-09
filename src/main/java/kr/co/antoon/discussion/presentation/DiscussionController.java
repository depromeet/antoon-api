package kr.co.antoon.discussion.presentation;

import kr.co.antoon.discussion.application.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;

}