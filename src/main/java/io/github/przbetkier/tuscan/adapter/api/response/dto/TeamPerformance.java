package io.github.przbetkier.tuscan.adapter.api.response.dto;

import io.github.przbetkier.tuscan.domain.CsgoMap;

public class TeamPerformance {

    private final CsgoMap map;
    private final Integer winRate;

    public TeamPerformance(CsgoMap map, Integer winRate) {
        this.map = map;
        this.winRate = winRate;
    }

    public CsgoMap getMap() {
        return map;
    }

    public Integer getWinRate() {
        return winRate;
    }
}
