package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails

class SamplePlayerDetailsResponse {

    public static ELO = 1251
    public static LEVEL = 4

    static def simple() {
        return new PlayerDetailsResponse(
                "playerId-1",
                "player",
                new GameDetails(
                        ELO,
                        LEVEL
                ),
                "avatar-url",
                "PL"
        )
    }
}
