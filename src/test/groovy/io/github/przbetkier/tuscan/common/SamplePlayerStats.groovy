package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.domain.player.dto.stats.Lifetime
import io.github.przbetkier.tuscan.domain.player.dto.stats.MapStatistics
import io.github.przbetkier.tuscan.domain.player.dto.stats.PlayerStats
import io.github.przbetkier.tuscan.domain.player.dto.stats.Segment

class SamplePlayerStats {

    static BEST_SOLO = "de_train"
    static BEST_TEAM = "de_mirage"

    static def simple(String kdRatio, String matches) {

        return new PlayerStats(
                new Lifetime(
                        "55",
                        matches,
                        kdRatio,
                        "55"
                ),
                [new Segment(
                        BEST_SOLO, // BECAUSE OF KD
                        new MapStatistics(
                                "15",
                                "1.95",
                                "22"
                        )
                ),
                 new Segment(
                         BEST_TEAM,  // BECAUSE OF WIN PERCENTAGE
                         new MapStatistics(
                                 "15",
                                 "1.10",
                                 "99"
                         )
                 ),
                 new Segment(
                         "de_overpass",
                         new MapStatistics(
                                 "15",
                                 "1.25",
                                 "11"
                         )
                 )]
        )
    }
}
