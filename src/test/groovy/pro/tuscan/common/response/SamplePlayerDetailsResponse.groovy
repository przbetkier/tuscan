package pro.tuscan.common.response

import groovy.transform.CompileStatic
import integration.common.MockedPlayer
import pro.tuscan.adapter.api.GameDetails
import pro.tuscan.adapter.api.Membership
import pro.tuscan.adapter.api.PlayerDetailsResponse

@CompileStatic
class SamplePlayerDetailsResponse {

    public static int ELO = 1251
    public static int LEVEL = 4

    static PlayerDetailsResponse simple(String nickname = MockedPlayer.NICKNAME) {
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
                Membership.FREE
        )
    }
}
