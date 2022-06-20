package kr.co.antoon.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.CoinRank;
import kr.co.antoon.user.domain.User;

public record GetUserDetailResponse(
        @Schema(description = "사용자 인덱스")
        Long id,

        @Schema(description = "이름")
        String name,

        @Schema(description = "이메일")
        String email,

        @Schema(description = "프로필 사진")
        String imageUrl,

        @Schema(description = "연령대")
        Integer age,

        @Schema(description = "보유한 코인 수")
        Long wallet,

        @Schema(description = "현재 개미 레벨")
        CoinRank coinRank
) {
        public GetUserDetailResponse(User user, AntCoinWallet wallet) {
                this(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getImageUrl(),
                        user.getAge(),
                        wallet.getWallet(),
                        wallet.getCoinRank()
                );
        }
}
