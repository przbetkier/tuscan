package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats;
import io.github.przbetkier.tuscan.domain.CsgoMap;
import io.github.przbetkier.tuscan.domain.player.dto.stats.PlayerStats;
import io.github.przbetkier.tuscan.domain.player.dto.stats.Segment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class PlayerStatsMapper {

    private PlayerStatsMapper() {
    }

    static PlayerCsgoStatsResponse map(PlayerStats playerStats) {
        List<MapStats> mapStats = mapToCsgoMapStats(playerStats);
        return new PlayerCsgoStatsResponse(
                mapToOverallStats(playerStats, mapStats),
                mapStats
        );
    }

    private static OverallStats mapToOverallStats(PlayerStats playerStats, List<MapStats> mapStats) {
        return new OverallStats(
                new BigDecimal(playerStats.getLifetime().getHeadshotPercentage()),
                new BigDecimal(playerStats.getLifetime().getKdRatio()),
                new Integer(playerStats.getLifetime().getMatches().replace(",", "")),
                new Integer(playerStats.getLifetime().getWinRate()),
                PlayerPerformanceMapper.map(mapStats),
                playerStats.getLifetime().getCurrentWinStreak(),
                playerStats.getLifetime().getLongestWinStreak());
    }

    private static List<MapStats> mapToCsgoMapStats(PlayerStats playerStats) {
        List<MapStats> mapStats = new ArrayList<>();
        playerStats.getSegments().stream()
                .filter(m -> m.getMode().equals("5v5"))
                .collect(Collectors.toList())
                .forEach(segment -> mapStats.add(mapSegmentToMapStats(segment)));
        return PlayerPerformanceMapper.orderFromHighestKdRatio(mapStats);
    }

    private static MapStats mapSegmentToMapStats(Segment segment) {
        return new MapStats(
                CsgoMap.valueOf(segment.getName().toUpperCase()),
                new Integer(segment.getMapStatistics().getMatches().replace(",", "")),
                new BigDecimal(segment.getMapStatistics().getKdRatio()),
                new Integer(segment.getMapStatistics().getWinPercentage())
        );
    }
}
