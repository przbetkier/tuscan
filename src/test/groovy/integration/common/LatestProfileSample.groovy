package integration.common

import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfile

import java.time.LocalDateTime

class LatestProfileSample {

    static def simple() {
        return new LatestProfile(
                "nickname",
                "http://avatar.url",
                3,
                1001,
                1.2,
                LocalDateTime.now()
        )
    }
}
