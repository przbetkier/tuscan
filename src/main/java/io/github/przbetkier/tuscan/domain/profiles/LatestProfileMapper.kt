package io.github.przbetkier.tuscan.domain.profiles

import io.github.przbetkier.tuscan.adapter.api.request.LatestProfileRequest
import java.time.Instant

class LatestProfileMapper {

    companion object {
        fun mapAndUpdate(request: LatestProfileRequest, instant: Instant): LatestProfile {
            return request.let {
                LatestProfile(it.nickname,
                        it.avatarUrl,
                        it.level,
                        it.elo,
                        it.kdRatio,
                        instant)
            }
        }
    }
}
