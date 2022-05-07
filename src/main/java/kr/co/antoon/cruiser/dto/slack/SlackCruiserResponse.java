package kr.co.antoon.cruiser.dto.slack;

public class SlackCruiserResponse {
    public static String count(long userCount, long discussionCount) {
        return "사용자수 : " + userCount + " | 토론수 : " + discussionCount;
    }
}