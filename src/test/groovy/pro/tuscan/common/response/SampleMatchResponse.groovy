package pro.tuscan.common.response

import groovy.transform.CompileStatic
import pro.tuscan.adapter.api.response.SimpleMatchesResponse
import pro.tuscan.adapter.api.response.dto.SimpleMatch

import java.time.LocalDateTime
import java.time.ZoneOffset

@CompileStatic
class SampleMatchResponse {

    static def simple() {
        new SimpleMatchesResponse(
                [
                        new SimpleMatch(
                                "matchId-1",
                                LocalDateTime.of(2018, 10, 1, 12, 30).toInstant(ZoneOffset.UTC),
                                LocalDateTime.of(2018, 10, 1, 13, 30).toInstant(ZoneOffset.UTC)),
                        new SimpleMatch(
                                "matchId-2",
                                LocalDateTime.of(2018, 10, 2, 12, 30).toInstant(ZoneOffset.UTC),
                                LocalDateTime.of(2018, 10, 2, 13, 30).toInstant(ZoneOffset.UTC)),
                        new SimpleMatch(
                                "matchId-3",
                                LocalDateTime.of(2018, 10, 3, 12, 30).toInstant(ZoneOffset.UTC),
                                LocalDateTime.of(2018, 10, 3, 13, 30).toInstant(ZoneOffset.UTC))
                ],
                3
        )
    }
}
