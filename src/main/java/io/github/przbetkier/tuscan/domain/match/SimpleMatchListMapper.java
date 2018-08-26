package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.SimpleMatch;
import io.github.przbetkier.tuscan.domain.match.dto.match.MatchesSimpleDetailsDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

class SimpleMatchListMapper {

    static SimpleMatchesResponse map(MatchesSimpleDetailsDto matchesDetails) {
        return new SimpleMatchesResponse(
                mapMatches(matchesDetails),
                matchesDetails.getMatchesCount());
    }

    private static List<SimpleMatch> mapMatches(MatchesSimpleDetailsDto matchesSimpleDetailsDto) {
        List<SimpleMatch> matches = new ArrayList<>();
        matchesSimpleDetailsDto.getSimpleMatchList().forEach(
                match -> matches.add(
                        new SimpleMatch(match.getMatchId(),
                                mapToLocalDate(match.getStartedAt()),
                                mapToLocalDate(match.getFinishedAt()))));
        return matches;
    }

    private static LocalDateTime mapToLocalDate(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
    }
}
