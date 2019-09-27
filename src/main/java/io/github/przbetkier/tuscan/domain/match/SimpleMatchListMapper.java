package io.github.przbetkier.tuscan.domain.match;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.SimpleMatch;
import io.github.przbetkier.tuscan.client.match.MatchesSimpleDetailsDto;
import io.github.przbetkier.tuscan.client.match.OpenMatchSimpleDetailsDto;

public class SimpleMatchListMapper {

    private SimpleMatchListMapper() {
    }

    public static SimpleMatchesResponse map(MatchesSimpleDetailsDto matchesDetails) {
        return new SimpleMatchesResponse(mapMatches(matchesDetails), matchesDetails.getMatchesCount());
    }

    public static SimpleMatchesResponse mapForOpenApi(List<OpenMatchSimpleDetailsDto> matchesSimpleDetailsDto) {
        return new SimpleMatchesResponse(mapMatchesFromOpenApi(matchesSimpleDetailsDto),
                                         matchesSimpleDetailsDto.size());
    }

    private static List<SimpleMatch> mapMatches(MatchesSimpleDetailsDto matchesSimpleDetailsDto) {
        List<SimpleMatch> matches = new ArrayList<>();
        matchesSimpleDetailsDto.getSimpleMatchList()
                .forEach(match -> matches.add(new SimpleMatch(match.getMatchId(),
                                                              mapToLocalDate(match.getStartedAt()),
                                                              mapToLocalDate(match.getFinishedAt()))));
        return matches;
    }

    private static List<SimpleMatch> mapMatchesFromOpenApi(List<OpenMatchSimpleDetailsDto> matchesSimpleDetailsDto) {
        List<SimpleMatch> matches = new ArrayList<>();
        // Timestamp returned from Open Faceit API is incorrect and has too many digits.
        matchesSimpleDetailsDto.forEach(match -> matches.add(new SimpleMatch(match.getId(),
                                                                             mapToLocalDate(match.getStartedAt() / 1000),
                                                                             mapToLocalDate(match.getFinishedAt() / 1000))));
        return matches;
    }

    private static LocalDateTime mapToLocalDate(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getTimeZone("UTC").toZoneId());
    }
}
