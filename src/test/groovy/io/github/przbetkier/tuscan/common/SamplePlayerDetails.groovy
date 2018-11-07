package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.client.player.Csgo
import io.github.przbetkier.tuscan.client.player.Games
import io.github.przbetkier.tuscan.client.player.PlayerDetails

class SamplePlayerDetails {

    public static Integer ELO = 1250
    public static Integer FACEIT_LEVEL = 4

    static simple(String playerId = "playerId", String nickname = "player") {
        new PlayerDetails(
                playerId,
                nickname,
                new Games(new Csgo(
                        ELO,
                        FACEIT_LEVEL,
                        "EU",
                        "10203040"
                )),
                "http://avatar-avatar.com/avatar",
                "PL"
        )
    }

    static withoutCsGoGame(String playerId = "playerId") {
        new PlayerDetails(
                playerId,
                "player",
                new Games(null),
                "http://avatar-avatar.com/avatar",
                "PL"
        )
    }
}
