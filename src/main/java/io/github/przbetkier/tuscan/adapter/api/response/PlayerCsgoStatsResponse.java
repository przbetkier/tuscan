package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats;

import java.util.List;

public class PlayerCsgoStatsResponse {

    private OverallStats overallStats;
    private List<MapStats> mapStats;

    public PlayerCsgoStatsResponse(OverallStats overallStats, List<MapStats> mapStats) {
        this.overallStats = overallStats;
        this.mapStats = mapStats;
    }

    public OverallStats getOverallStats() {
        return overallStats;
    }

    public List<MapStats> getMapStats() {
        return mapStats;
    }
}
