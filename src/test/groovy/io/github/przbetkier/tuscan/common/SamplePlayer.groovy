package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.adapter.api.response.dto.Player
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats

class SamplePlayer {

    static def simple() {
        return new Player(
                "playerId-1",
                "player",
                new PlayerStats(
                        20,
                        3,
                        10,
                        5,
                        25,
                        2.0,
                        1.12,
                        4,
                        0,
                        1,
                        0
                )
        )
    }
}
