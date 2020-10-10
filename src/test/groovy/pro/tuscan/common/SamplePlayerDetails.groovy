package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.client.player.Ban
import pro.tuscan.client.player.Csgo
import pro.tuscan.client.player.Games
import pro.tuscan.client.player.PlayerDetails

import java.time.ZonedDateTime

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID

@CompileStatic
class SamplePlayerDetails {

    public static Integer ELO = 1250
    public static Integer FACEIT_LEVEL = 4

    static simple(String playerId = PLAYER_ID, String nickname = NICKNAME) {
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
                ["FREE"]
        )
    }

    static withoutCsGoGame(String playerId = PLAYER_ID) {
        new PlayerDetails(
                playerId,
                NICKNAME,
                new Games(null),
                "http://avatar-avatar.com/avatar",
                "PL",
                ["FREE"]
        )
    }
}
