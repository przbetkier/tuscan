package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfile

import java.time.LocalDateTime

class SampleLatestProfile {

    def static simple(String nickname = "player-1", date = LocalDateTime.now()) {
        return new LatestProfile(
                nickname,
                "https://avatar.url",
                5,
                1000,
                1.21,
                date
        )

    }
}
