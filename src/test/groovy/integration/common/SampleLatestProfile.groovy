package integration.common

import groovy.transform.CompileStatic
import pro.tuscan.domain.profiles.LatestProfile

import java.time.Instant

import static integration.common.MockedPlayer.NICKNAME

@CompileStatic
class SampleLatestProfile {

    static def simple(String nickname = NICKNAME) {
        return new LatestProfile(
                nickname,
                "http://avatar.url",
                3,
                1001,
                1.2,
                Instant.now()
        )
    }
}
