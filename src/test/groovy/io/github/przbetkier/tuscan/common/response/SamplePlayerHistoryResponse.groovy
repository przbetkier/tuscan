package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchHistory

import java.time.Instant
import java.time.LocalDateTime

class SamplePlayerHistoryResponse {

    static def simple() {
        return new PlayerHistoryResponse(
                [new MatchHistory(
                        "match-1",
                        Instant.now(),
                        2000,
                        20,
                        1.34,
                        35
                ),
                 new MatchHistory(
                         "match-2",
                         Instant.now(),
                         1080,
                         17,
                         1.12,
                         90
                 ),
                 new MatchHistory(
                         "match-3",
                         Instant.now(),
                         1063,
                         10,
                         0.22,
                         22
                 )]
        )
    }
}
