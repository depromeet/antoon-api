package kr.co.antoon.graph.criteria;

public class BasicAllocateScore implements ScoreAllocationCriteria {
    public int webtoonScore(Double score) {
        return (int) (score * 100 / 2);
    }

    public int discussionScore(long count) {
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

    public int recommendationScore(int count) {
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

    public int graphScore(int discussionScore, int recommendationScore, int webtoonScore) {
        var graphScore = discussionScore + recommendationScore + webtoonScore;

        return Math.max(graphScore, 250);
    }

    public static double getDifferencePercentage(int graphScore, int scoreGap) {
        return scoreGap * 1.0 / graphScore * 100.0;
    }
}