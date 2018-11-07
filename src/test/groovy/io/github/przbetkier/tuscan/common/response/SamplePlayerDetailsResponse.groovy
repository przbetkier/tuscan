package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails

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
                        "EU"
                ),
                "avatar-url",
                "PL",
                "2897328939"
        )
    }
}
