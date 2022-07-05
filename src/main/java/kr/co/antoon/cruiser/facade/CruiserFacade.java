package kr.co.antoon.cruiser.facade;

import kr.co.antoon.coin.application.AntCoinWalletService;
import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.cruiser.dto.slack.CruiserRequest;
import kr.co.antoon.cruiser.dto.slack.SlackCruiserResponse;
import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
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
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final TopRankService topRankService;
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final AntCoinWalletService antCoinWalletService;

    @Transactional(readOnly = true)
    public CruiserRequest statistics() {
        var userCount = userService.count();
        var discussionCount = discussionService.count();
        var publishWebtoonCount = webtoonService.countByStatus(ActiveStatus.PUBLISH);
        var pauseWebtoonCount = webtoonService.countByStatus(ActiveStatus.PAUSE);
        var webtoonSnapshotCount = webtoonSnapshotService.count();
        var graphScoreSnapshotCount = graphScoreSnapshotService.count();
        var discussionLikeCount = discussionService.countAllLikes();
        var totalCoin = antCoinWalletService.findAll()
                .stream().mapToLong(AntCoinWallet::getWallet).sum();

        var response = SlackCruiserResponse.dataStatistics(
                userCount,
                discussionCount,
                discussionLikeCount,
                publishWebtoonCount,
                pauseWebtoonCount,
                webtoonSnapshotCount,
                graphScoreSnapshotCount,
                totalCoin
        );

        return new CruiserRequest(response);
    }

    @Transactional(readOnly = true)
    public CruiserRequest topRanks() {
        var topRanks = topRankService.findTopRank()
                .parallelStream()
                .map(rank -> {
                            var webtoon = webtoonService.findById(rank.getWebtoonId());
                            return "\n*" + rank.getRanking() + "위* : " + webtoon.getTitle();
                        }
                ).toList();
        
        var response = SlackCruiserResponse.topRanks(topRanks);

        return new CruiserRequest(response);
    }
}
