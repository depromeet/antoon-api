package kr.co.antoon.graph.domain;

public class ScoreAllocationCriteria {
    public static int DEFAULT_WEBTOON_SCORE = 100;

    public static int webtoonScore(Double score) {
        return (int) (score * 100 / 2);
    }

    public static int discussionScore(long count) {
        if (count > 100) {
            return 200;
        } else if (count > 50) {
            return 150;
        } else if (count > 0) {
            return 100;
        } else {
            return 50;
        }
    }

    public static int DEFAULT_RECOMMENDATION_SCORE = 100;

    public static int recommendationScore(int count) {
        if (count > 100) {
            return 300;
        } else if (count > 50) {
            return 200;
        } else {
            return 150;
        }
    }

    public static int graphScore(int discussionScore, int recommendationScore, int webtoonScore) {
        var graphScore = discussionScore + recommendationScore + webtoonScore;
        return Math.max(graphScore, 250);
    }
}