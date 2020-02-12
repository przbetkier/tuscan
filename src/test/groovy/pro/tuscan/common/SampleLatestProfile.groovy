package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.domain.profiles.LatestProfile

import java.time.Instant

import static integration.common.MockedPlayer.NICKNAME

@CompileStatic
class SampleLatestProfile {

    def static simple(String nickname = NICKNAME, Instant instant = Instant.now()) {
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
