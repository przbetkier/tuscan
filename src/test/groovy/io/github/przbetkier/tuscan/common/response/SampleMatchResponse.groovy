package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.SimpleMatch

import java.time.LocalDateTime

class SampleMatchResponse {

    static def simple() {
        new SimpleMatchesResponse(
                [
                        new SimpleMatch(
                                "matchId-1",
                                LocalDateTime.of(2018, 10, 1, 12, 30),
                                LocalDateTime.of(2018, 10, 1, 13, 30)),
                        new SimpleMatch(
                                "matchId-2",
                                LocalDateTime.of(2018, 10, 2, 12, 30),
                                LocalDateTime.of(2018, 10, 2, 13, 30)),
                        new SimpleMatch(
                                "matchId-3",
                                LocalDateTime.of(2018, 10, 3, 12, 30),
                                LocalDateTime.of(2018, 10, 3, 13, 30))
                ],
                3
        )
    }
}
