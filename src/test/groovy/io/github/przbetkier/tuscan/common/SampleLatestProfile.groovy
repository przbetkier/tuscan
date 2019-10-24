package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.domain.profiles.LatestProfile

import java.time.Instant

import static integration.common.MockedPlayer.NICKNAME

class SampleLatestProfile {

    def static simple(String nickname = NICKNAME, instant = Instant.now()) {
        return new LatestProfile(
                nickname,
                "https://avatar.url",
                5,
                1000,
                1.21,
                instant
        )
    }
}
