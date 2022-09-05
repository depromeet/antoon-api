package kr.co.antoon.criteria;

/**
 * @apiNote graph score criteria
 **/
public interface ScoreAllocationCriteria {
    /**
     * @param score webtoon platform score
     * @apiNote webtoon platform score
     * max score 500
     **/
    int webtoonScore(Double score);

    /**
     * @param count webtoon discussion count
     * @apiNote webtoon discussion count to score
     * max score 200
     **/
    int discussionScore(long count);

    /**
     * @param count webtoon status count
     * @apiNote webtoon status count to score
     * max score 300
     **/
    int webtoonStatusScore(int count);

    /**
     * @param webtoonScore        50% 500
     * @param discussionScore     20% 200
     * @param webtoonStatusScore 30% 300
     * @apiNote graph score max 1000
     **/
    int graphScore(int discussionScore, int webtoonStatusScore, int webtoonScore);
}