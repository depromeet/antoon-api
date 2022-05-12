package kr.co.antoon.graph.domain;

public class ScoreAllocationCriteria {
    public static int webtoonScore(Double score) {
        return (int) (score * 100 / 2);
    }

    public static int discussionScore(long count) {
        if (count > 10) {
            return 200;
        } else if (count > 8) {
            return 150;
        } else if (count > 6) {
            return 125;
        } else if (count > 4) {
            return 100;
        } else if (count > 2) {
            return 75;
        } else {
            return 50;
        }
    }

    public static int recommendationScore(int count) {
        if (count > 10) {
            return 300;
        } else if (count > 8) {
            return 250;
        } else if (count > 6) {
            return 225;
        } else if (count > 4) {
            return 200;
        } else if (count > 2) {
            return 150;
        } else {
            return 100;
        }
    }

    public static int graphScore(int discussionScore, int recommendationScore, int webtoonScore) {
        var graphScore = discussionScore + recommendationScore + webtoonScore;
        return Math.max(graphScore, 250);
    }
}