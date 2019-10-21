package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchHistory;
import io.github.przbetkier.tuscan.client.player.MatchHistoryDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.util.TimeZone.getDefault;

public class PlayerHistoryMapper {

    private static final int STARTING_ELO_POINTS = 1000;
    private static final int MAX_MATCHES_COUNT = 100;

    private PlayerHistoryMapper() {
    }

    public static PlayerHistoryResponse map(List<MatchHistoryDto> historyMatches) {

        historyMatches = filterHistory(historyMatches);

        List<MatchHistory> matchHistoryList = new ArrayList<>();
        int historySize = historyMatches.size();

        List<MatchHistoryDto> lastMatches = getLastMatchesFromHistory(historyMatches, historySize);

        // Loop over all matches excluding last
        for (int i = 0; i < lastMatches.size() - 1; i++) {
            MatchHistoryDto currentMatch = lastMatches.get(i);
            MatchHistoryDto matchBefore = lastMatches.get(i + 1);

            // Make sure if match before has elo registered otherwise find next match with elo to calculate gain/loss
            int index = i + 1;
            while (!matchBefore.hasElo() && index < lastMatches.size()) {
                matchBefore = lastMatches.get(index);
                index++;
            }

            Integer eloBeforeMatch = matchBefore.hasElo() ? convertToElo(matchBefore.getElo()) : STARTING_ELO_POINTS;
            Integer eloAfterMatch = getEloAfterMatch(currentMatch, matchBefore);

            MatchHistory matchHistoryToAdd = new MatchHistory(currentMatch.getMatchId(),
                                                              getLocalDateTimeFromTimestamp(currentMatch.getDate()),
                                                              eloAfterMatch,
                                                              eloAfterMatch - eloBeforeMatch,
                                                              new BigDecimal(currentMatch.getKdRatio()),
                                                              Integer.parseInt(currentMatch.getHsPercentage()));
            matchHistoryList.add(matchHistoryToAdd);
        }

        // If matches list has less matches than MAX_MATCHES_COUNT it indicates that
        // the last match is his first ever played
        if (lastMatches.size() <= MAX_MATCHES_COUNT) {
            MatchHistoryDto firstMatch = historyMatches.get(historySize - 1);

            Integer eloAfter;
            Integer eloBefore;

            if (firstMatch.hasElo()) {
                eloAfter = convertToElo(firstMatch.getElo());
                eloBefore = STARTING_ELO_POINTS;
            } else {
                eloAfter = STARTING_ELO_POINTS;
                eloBefore = STARTING_ELO_POINTS;
            }

            matchHistoryList.add(new MatchHistory(firstMatch.getMatchId(),
                                                  getLocalDateTimeFromTimestamp(firstMatch.getDate()),
                                                  eloAfter,
                                                  (eloAfter - eloBefore),
                                                  new BigDecimal(firstMatch.getKdRatio()),
                                                  Integer.parseInt(firstMatch.getHsPercentage())));
        }
        return new PlayerHistoryResponse(matchHistoryList);
    }
    private static Integer getEloAfterMatch(MatchHistoryDto currentMatch, MatchHistoryDto matchBefore) {
        return currentMatch.hasElo() ? convertToElo(currentMatch.getElo()) : getPreviousOrDefault(matchBefore);
    }

    private static Integer getPreviousOrDefault(MatchHistoryDto matchBefore) {
        return matchBefore.hasElo() ? convertToElo(matchBefore.getElo()) : 0;
    }

    private static List<MatchHistoryDto> filterHistory(List<MatchHistoryDto> matches) {
        return matches.stream().filter(m -> m.getMode().equals("5v5")).collect(Collectors.toList());
    }

    private static List<MatchHistoryDto> getLastMatchesFromHistory(List<MatchHistoryDto> matches, int historySize) {

        return historySize > MAX_MATCHES_COUNT
                ? matches.subList(0, MAX_MATCHES_COUNT + 1)
                : matches.subList(0, historySize);
    }

    private static Integer convertToElo(String eloString) {
        return Integer.valueOf(eloString.replace(",", ""));
    }

    private static LocalDateTime getLocalDateTimeFromTimestamp(long timestamp) {
        return ofInstant(ofEpochMilli(timestamp), getDefault().toZoneId());
    }
}
