package io.github.przbetkier.tuscan.common.response

import integration.common.MockedPlayer
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.BanInfo
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails
import io.github.przbetkier.tuscan.client.player.Membership

class SamplePlayerDetailsResponse {

    public static ELO = 1251
    public static LEVEL = 4

    static def simple(String nickname = MockedPlayer.NICKNAME) {
        return new PlayerDetailsResponse(
                MockedPlayer.NICKNAME,
                nickname,
                new GameDetails(
                        ELO,
                        LEVEL,
                        "EU",
                        "2897328939"
                ),
                "avatar-url",
                "PL",
                Membership.FREE,
                new BanInfo(false)
        )
    }
}
