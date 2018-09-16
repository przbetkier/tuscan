package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.math.BigDecimal;

public class PlayerStats {

    private final Integer kills;
    private final Integer assists;
    private final Integer deaths;
    private final Integer headshots;
    private final Integer headshotPercentage;
    private final BigDecimal kdRatio;
    private final BigDecimal krRatio;

    public PlayerStats(Integer kills, Integer assists, Integer deaths, Integer headshots, Integer headshotPercentage,
                       BigDecimal kdRatio, BigDecimal krRatio) {
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.headshots = headshots;
        this.headshotPercentage = headshotPercentage;
        this.kdRatio = kdRatio;
        this.krRatio = krRatio;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getHeadshots() {
        return headshots;
    }

    public Integer getHeadshotPercentage() {
        return headshotPercentage;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }

    public BigDecimal getKrRatio() {
        return krRatio;
    }
}
