package pro.tuscan.domain.profiles

import pro.tuscan.adapter.api.LatestProfileRequest
import java.time.Instant

class LatestProfileMapper {

    companion object {
        fun mapAndUpdate(request: LatestProfileRequest, instant: Instant): LatestProfile =
                request.let {
                    LatestProfile(
                            nickname = it.nickname,
                            avatarUrl = it.avatarUrl,
                            level = it.level,
                            elo = it.elo,
                            kdRatio = it.kdRatio,
                            createdOn = instant)
                }
    }
}
