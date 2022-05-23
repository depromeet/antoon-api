package kr.co.antoon.cruiser.domain;

public interface Cruiser {
    /**
     * Cruiser For Message
     *
     * @param content 알림 메세지
     * @apiNote 알림 서비스를 제공하는 최상위 인터페이스
     **/
    void send(String content);
}