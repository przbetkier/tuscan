package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.SimpleMatch;
import io.github.przbetkier.tuscan.domain.match.dto.match.MatchesSimpleDetailsDto;
import java.util.HashSet;
import java.util.Set;

class SimpleMatchListMapper {

    static SimpleMatchesResponse map(MatchesSimpleDetailsDto matchesDetails) {
        return new SimpleMatchesResponse(
                mapMatches(matchesDetails),
                matchesDetails.getMatchesCount());
    }

    private static Set<SimpleMatch> mapMatches(MatchesSimpleDetailsDto matchesSimpleDetailsDto) {
        Set<SimpleMatch> matches = new HashSet<>();
        matchesSimpleDetailsDto.getSimpleMatchList().forEach(
                match -> matches.add(
                        new SimpleMatch(match.getMatchId(), match.getStartedAt(), match.getFinishedAt()))
        );
        return matches;
    }
}
