package io.github.przbetkier.tuscan.domain.match;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
                                                              mapToInstant(match.getStartedAt()),
                                                              mapToInstant(match.getFinishedAt()))));
        return matches;
    }

    private static List<SimpleMatch> mapMatchesFromOpenApi(List<OpenMatchSimpleDetailsDto> matchesSimpleDetailsDto) {
        List<SimpleMatch> matches = new ArrayList<>();
        // Timestamp returned from Open Faceit API is incorrect and has too many digits.
        matchesSimpleDetailsDto.forEach(match -> matches.add(new SimpleMatch(match.getId(),
                                                                             mapToInstant(match.getStartedAt() / 1000),
                                                                             mapToInstant(match.getFinishedAt() / 1000))));
        return matches;
    }

    private static Instant mapToInstant(long timestamp) {
        return Instant.ofEpochMilli(timestamp);
    }
}
