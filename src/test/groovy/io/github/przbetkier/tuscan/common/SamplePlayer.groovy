package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.adapter.api.response.dto.Player
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID

class SamplePlayer {

    static def simple() {
        return new Player(
                PLAYER_ID,
                NICKNAME,
                new PlayerStats(20, 3, 10, 5, 25, 2.0,
                        1.12, 4, 0, 1, 0
                )
        )
    }
}
