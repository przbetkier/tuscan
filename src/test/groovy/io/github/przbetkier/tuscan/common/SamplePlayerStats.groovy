package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.client.player.Lifetime
import io.github.przbetkier.tuscan.client.player.MapStatistics
import io.github.przbetkier.tuscan.client.player.PlayerStats
import io.github.przbetkier.tuscan.client.player.Segment

class SamplePlayerStats {

    static BEST_SOLO = "de_train"
    static BEST_TEAM = "de_mirage"

    static def simple(String kdRatio, String matches) {

        return new PlayerStats(
                new Lifetime(
                        "55",
                        matches,
                        kdRatio,
                        "55",
                        2,
                        9
                ),
                [new Segment(
                        BEST_SOLO, // BECAUSE OF KD
                        new MapStatistics(
                                "15",
                                "1.95",
                                "22"
                        ),
                        "5v5"
                ),
                 new Segment(
                         BEST_TEAM,  // BECAUSE OF WIN PERCENTAGE
                         new MapStatistics(
                                 "15",
                                 "1.10",
                                 "99"
                         ),
                         "5v5"
                 ),
                 new Segment(
                         "de_overpass",
                         new MapStatistics(
                                 "15",
                                 "1.25",
                                 "11"
                         ),
                         "5v5"
                 )]
        )
    }
}
