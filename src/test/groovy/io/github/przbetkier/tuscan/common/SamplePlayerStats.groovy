package io.github.przbetkier.tuscan.common

import groovy.transform.CompileStatic
import io.github.przbetkier.tuscan.client.player.Lifetime
import io.github.przbetkier.tuscan.client.player.MapStatistics
import io.github.przbetkier.tuscan.client.player.PlayerStats
import io.github.przbetkier.tuscan.client.player.Segment

@CompileStatic
class SamplePlayerStats {

    static String BEST_SOLO = "de_train"
    static String BEST_TEAM = "de_mirage"
    static String WORST_SOLO = "de_mirage"
    static String WORST_TEAM = "de_train"

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
                                "22.3",
                                "51",
                                "33",
                                "5",
                                "3",
                                "2",
                                "1"
                        ),
                        "5v5"
                ),
                 new Segment(
                         BEST_TEAM,  // BECAUSE OF WIN PERCENTAGE
                         new MapStatistics(
                                 "15",
                                 "1.10",
                                 "18.3",
                                 "25",
                                 "67",
                                 "10",
                                 "10",
                                 "2",
                                 "1"
                         ),
                         "5v5"
                 ),
                 new Segment(
                         "de_overpass",
                         new MapStatistics(
                                 "400",
                                 "1.25",
                                 "11.13",
                                 "37",
                                 "50",
                                 "200",
                                 "10",
                                 "0",
                                 "1"
                         ),
                         "5v5"
                 )]
        )
    }
}
