package kr.co.antoon.cruiser.domain;

public interface Cruiser {
    /**
     * Cruiser For Message
     *
     * @param content 알림 메세지
     **/
    void send(String content);
}