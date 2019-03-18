package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats;
import io.github.przbetkier.tuscan.client.player.PlayerStats;
import io.github.przbetkier.tuscan.client.player.Segment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.przbetkier.tuscan.domain.CsgoMap.isInMapPool;
import static io.github.przbetkier.tuscan.domain.CsgoMap.valueOf;

public class PlayerStatsMapper {

    private PlayerStatsMapper() {
    }

    public static PlayerCsgoStatsResponse map(PlayerStats playerStats) {
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
                format(playerStats.getLifetime().getMatches()),
                Integer.valueOf(playerStats.getLifetime().getWinRate()),
                PlayerPerformanceMapper.map(mapStats),
                playerStats.getLifetime().getCurrentWinStreak(),
                playerStats.getLifetime().getLongestWinStreak());
    }

    private static List<MapStats> mapToCsgoMapStats(PlayerStats playerStats) {
        List<MapStats> mapStats = new ArrayList<>();
        playerStats.getSegments().stream()
                .filter(m -> m.getMode().equals("5v5") && isInMapPool(m.getName()))
                .collect(Collectors.toList())
                .forEach(segment -> mapStats.add(mapSegmentToMapStats(segment)));
        return PlayerPerformanceMapper.orderFromHighestKdRatio(mapStats);
    }

    private static MapStats mapSegmentToMapStats(Segment segment) {
        return new MapStats(
                valueOf(segment.getName().toUpperCase()),
                new Integer(segment.getMapStatistics().getMatches().replace(",", "")),
                new BigDecimal(segment.getMapStatistics().getKdRatio()),
                format(segment.getMapStatistics().getWins()),
                new Integer(segment.getMapStatistics().getWinPercentage()),
                new Integer(segment.getMapStatistics().getHsPercentage()),
                new BigDecimal(segment.getMapStatistics().getAverageKills()),
                format(segment.getMapStatistics().getTripleKills()),
                format(segment.getMapStatistics().getQuadroKills()),
                format(segment.getMapStatistics().getPentaKills()));
    }

    private static Integer format(String number) {
        return new Integer(number.replace(",", ""));
    }
}
