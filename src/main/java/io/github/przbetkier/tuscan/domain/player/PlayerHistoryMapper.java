package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchHistory;
import io.github.przbetkier.tuscan.domain.player.dto.MatchHistoryDto;
import io.github.przbetkier.tuscan.domain.player.dto.PlayerHistoryDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

class PlayerHistoryMapper {

    private final static Integer MAX_MATCHES_COUNT = 21;

    private PlayerHistoryMapper() {
    }

    static PlayerHistoryResponse map(PlayerHistoryDto history) {
        List<MatchHistory> matchHistoryList = new ArrayList<>();

        List<MatchHistoryDto> lastMatches = new ArrayList<>(history.getMatchHistoryDtoList().subList(0, MAX_MATCHES_COUNT));

        for (int i = 0; i < lastMatches.size() - 1; i++) {
            MatchHistoryDto matchAfter = lastMatches.get(i);
            MatchHistoryDto matchBefore = lastMatches.get(i + 1);

            Integer eloAfter;
            Integer eloBefore;

            if (matchAfter.getElo() != null && matchBefore.getElo() != null) {
                eloAfter = Integer.valueOf(matchAfter.getElo().replace(",", ""));
                eloBefore = Integer.valueOf(matchBefore.getElo().replace(",", ""));
            } else {
                eloAfter = 0;
                eloBefore = 0;
            }

            MatchHistory matchHistoryToAdd = new MatchHistory(
                    matchAfter.getMatchId(),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(matchAfter.getDate()),
                            TimeZone.getDefault().toZoneId()),
                    eloAfter,
                    eloAfter - eloBefore
            );
            matchHistoryList.add(matchHistoryToAdd);
        }
        return new PlayerHistoryResponse(matchHistoryList);
    }
}
