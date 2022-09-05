package kr.co.antoon.coin.domain.vo;

import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemittanceType {
    SIGNED_SERVICE("가입 리워드"),
    JOINED_WEBTOON("웹툰 탑승"),
    LEAVED_WEBTOON("웹툰 하차"),
    JOINED_CHARACTER("인물/커플 탑승"),
    VOTE("투표 참여"),
    ;

    private final String description;

    public static RemittanceType joinOrLeave(WebtoonStatusType status) {
        if(status.toString().startsWith("JOIN")) {
            return JOINED_WEBTOON;
        }
        return LEAVED_WEBTOON;
    }
}
