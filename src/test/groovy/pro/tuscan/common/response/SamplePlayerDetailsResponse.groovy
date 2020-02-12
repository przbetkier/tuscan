package pro.tuscan.common.response

import groovy.transform.CompileStatic
import integration.common.MockedPlayer
import pro.tuscan.adapter.api.response.PlayerDetailsResponse
import pro.tuscan.adapter.api.response.dto.BanInfo
import pro.tuscan.adapter.api.response.dto.GameDetails
import pro.tuscan.client.player.Membership

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
                Membership.FREE,
                new BanInfo(false)
        )
    }
}
