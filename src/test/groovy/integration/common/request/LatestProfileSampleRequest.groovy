package integration.common.request

import integration.common.MockedPlayer
import pro.tuscan.adapter.api.request.LatestProfileRequest

class LatestProfileSampleRequest {

    static LatestProfileRequest simple(String nickname = MockedPlayer.NICKNAME, int level = 6, BigDecimal kdRatio = 1.1, int elo = 1420) {
        return new LatestProfileRequest(
                nickname,
                "http://avatar.url",
                level,
                elo,
                kdRatio
        )
    }
}
