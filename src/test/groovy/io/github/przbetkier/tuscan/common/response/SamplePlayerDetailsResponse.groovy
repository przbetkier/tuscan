package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails
import io.github.przbetkier.tuscan.client.player.Membership

class SamplePlayerDetailsResponse {

    public static ELO = 1251
    public static LEVEL = 4

    static def simple(String nickname = "player") {
        return new PlayerDetailsResponse(
                "playerId-1",
                nickname,
                new GameDetails(
                        ELO,
                        LEVEL,
                        "EU",
                        "2897328939"
                ),
                "avatar-url",
                "PL",
                Membership.FREE
        )
    }
}
