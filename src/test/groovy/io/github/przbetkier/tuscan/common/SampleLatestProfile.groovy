package io.github.przbetkier.tuscan.common

import groovy.transform.CompileStatic
import io.github.przbetkier.tuscan.domain.profiles.LatestProfile

import java.time.LocalDateTime

import static integration.common.MockedPlayer.NICKNAME

@CompileStatic
class SampleLatestProfile {

    def static simple(String nickname = NICKNAME, LocalDateTime date = LocalDateTime.now()) {
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
