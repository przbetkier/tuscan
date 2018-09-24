package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats
import io.github.przbetkier.tuscan.domain.CsgoMap

class SamplePlayerCsgoStats {

    static def simple() {
        return new PlayerCsgoStatsResponse(
                new OverallStats(
                        new BigDecimal(42),
                        new BigDecimal(1.32),
                        21,
                        42,
                        CsgoMap.DE_NUKE,
                        CsgoMap.DE_INFERNO
                ),
                [new MapStats(
                        CsgoMap.DE_NUKE,
                        3,
                        new BigDecimal(1.33),
                        90
                ),
                 new MapStats(
                         CsgoMap.DE_TRAIN,
                         3,
                         new BigDecimal(1.11),
                         90
                 ),
                 new MapStats(
                         CsgoMap.DE_INFERNO,
                         3,
                         new BigDecimal(0.99),
                         90
                 )]
        )
    }
}
