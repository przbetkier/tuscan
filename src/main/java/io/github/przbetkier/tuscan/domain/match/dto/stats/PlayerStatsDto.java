package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStatsDto {

    private String kills;
    private String assists;
    private String deaths;
    private String headshots;
    private String headshotPercentage;
    private String kdRatio;
    private String krRatio;
    private String mvps;
    private String pentaKills;
    private String quadroKills;
    private String tripleKills;

    @JsonCreator
    public PlayerStatsDto(@JsonProperty("Kills") String kills,
                          @JsonProperty("Assists") String assists,
                          @JsonProperty("Deaths") String deaths,
                          @JsonProperty("Headshot") String headshots,
                          @JsonProperty("Headshots %") String headshotPercentage,
                          @JsonProperty("K/D Ratio") String kdRatio,
                          @JsonProperty("K/R Ratio") String krRatio,
                          @JsonProperty("MVPs") String mvps,
                          @JsonProperty("Penta Kills") String pentaKills,
                          @JsonProperty("Quadro Kills") String quadroKills,
                          @JsonProperty("Triple Kills") String tripleKills) {
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.headshots = headshots;
        this.headshotPercentage = headshotPercentage;
        this.kdRatio = kdRatio;
        this.krRatio = krRatio;
        this.mvps = mvps;
        this.pentaKills = pentaKills;
        this.quadroKills = quadroKills;
        this.tripleKills = tripleKills;
    }

    public String getKills() {
        return kills;
    }

    public String getAssists() {
        return assists;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getHeadshots() {
        return headshots;
    }

    public String getHeadshotPercentage() {
        return headshotPercentage;
    }

    public String getKdRatio() {
        return kdRatio;
    }

    public String getKrRatio() {
        return krRatio;
    }

    public String getMvps() {
        return mvps;
    }

    public String getPentaKills() {
        return pentaKills;
    }

    public String getQuadroKills() {
        return quadroKills;
    }

    public String getTripleKills() {
        return tripleKills;
    }
}
