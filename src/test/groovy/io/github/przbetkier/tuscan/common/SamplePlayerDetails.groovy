package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.client.player.Ban
import io.github.przbetkier.tuscan.client.player.Csgo
import io.github.przbetkier.tuscan.client.player.Games
import io.github.przbetkier.tuscan.client.player.PlayerDetails

import java.time.ZonedDateTime

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
                "PL",
                ["FREE"],
                []
        )
    }

    static withoutCsGoGame(String playerId = "playerId") {
        new PlayerDetails(
                playerId,
                "player",
                new Games(null),
                "http://avatar-avatar.com/avatar",
                "PL",
                ["FREE"],
                []
        )
    }

    static banned(String playerId = "playerId", ZonedDateTime dateTime) {
        new PlayerDetails(
                playerId,
                "player",
                new Games(new Csgo(1, 1, "", "")),
                "http://avatar-avatar.com/avatar",
                "PL",
                ["FREE"],
                [new Ban(ZonedDateTime.now(), dateTime, "cheater")]
        )
    }
}
