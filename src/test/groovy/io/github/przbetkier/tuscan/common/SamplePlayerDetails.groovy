package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.domain.player.PlayerDetails
import io.github.przbetkier.tuscan.domain.player.dto.Csgo
import io.github.przbetkier.tuscan.domain.player.dto.Games

class SamplePlayerDetails {

    public static Integer ELO = 1250
    public static Integer FACEIT_LEVEL = 4

    static simple(String playerId = "playerId") {
        new PlayerDetails(
                playerId,
                "player",
                new Games(new Csgo(
                        ELO,
                        FACEIT_LEVEL
                )),
                "http://avatar-avatar.com/avatar",
                "PL"
        )
    }

    static withoutCsGoGame(String playerId = "playerId") {
        new PlayerDetails(
                playerId,
                "player",
                new Games(
                        null
                ),
                "http://avatar-avatar.com/avatar",
                "PL"
        )
    }
}
