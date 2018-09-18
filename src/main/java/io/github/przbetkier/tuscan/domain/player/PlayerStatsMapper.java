package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats;
import io.github.przbetkier.tuscan.domain.player.dto.stats.*;
import io.github.przbetkier.tuscan.domain.CsgoMap;
import io.github.przbetkier.tuscan.domain.player.dto.stats.Segment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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
                orderFromHighestKdRatio(mapStats).get(0).getCsgoMap(),
                orderFromHighestWinRate(mapStats).get(0).getCsgoMap()
                );
    }

    private static List<MapStats> mapToCsgoMapStats(PlayerStats playerStats) {
        List<MapStats> mapStats = new ArrayList<>();
        playerStats.getSegments().forEach(segment -> mapStats.add(mapSegmentToMapStats(segment)));
        return orderFromHighestKdRatio(mapStats);
    }

    private static MapStats mapSegmentToMapStats(Segment segment) {
        return new MapStats(
                CsgoMap.valueOf(segment.getName().toUpperCase()),
                new Integer(segment.getMapStatistics().getMatches().replace(",", "")),
                new BigDecimal(segment.getMapStatistics().getKdRatio()),
                new Integer(segment.getMapStatistics().getWinPercentage())
        );
    }

    private static List<MapStats> orderFromHighestKdRatio(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getKdRatio).reversed())
                .collect(Collectors.toList());
    }

    private static List<MapStats> orderFromHighestWinRate(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getWinPercentage).reversed())
                .collect(Collectors.toList());
    }
}
