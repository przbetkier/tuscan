package pro.tuscan.adapter.api.response.dto;

import pro.tuscan.domain.CsgoMap;

import java.math.BigDecimal;

public class MapStats {

    private final CsgoMap csgoMap;
    private final Integer matches;
    private final BigDecimal kdRatio;
    private final Integer wins;
    private final Integer winPercentage;
    private final Integer hsPercentage;
    private final BigDecimal averageKills;
    private final Integer tripleKills;
    private final Integer quadroKills;
    private final Integer pentaKills;

    public MapStats(CsgoMap csgoMap, Integer matches, BigDecimal kdRatio, Integer wins, Integer winPercentage,
                    Integer hsPercentage, BigDecimal averageKills, Integer tripleKills, Integer quadroKills,
                    Integer pentaKills) {
        this.csgoMap = csgoMap;
        this.matches = matches;
        this.kdRatio = kdRatio;
        this.wins = wins;
        this.winPercentage = winPercentage;
        this.hsPercentage = hsPercentage;
        this.averageKills = averageKills;
        this.tripleKills = tripleKills;
        this.quadroKills = quadroKills;
        this.pentaKills = pentaKills;
    }

    public CsgoMap getCsgoMap() {
        return csgoMap;
    }

    public Integer getMatches() {
        return matches;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }

    public Integer getWinPercentage() {
        return winPercentage;
    }

    public Integer getHsPercentage() {
        return hsPercentage;
    }

    public Integer getTripleKills() {
        return tripleKills;
    }

    public Integer getQuadroKills() {
        return quadroKills;
    }

    public Integer getPentaKills() {
        return pentaKills;
    }

    public Integer getWins() {
        return wins;
    }

    public BigDecimal getAverageKills() {
        return averageKills;
    }
}
