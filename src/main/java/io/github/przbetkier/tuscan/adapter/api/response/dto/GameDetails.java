package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class GameDetails {

    private final Integer faceitElo;
    private final Integer level;
    private final String region;

    public GameDetails(Integer faceitElo, Integer level, String region) {
        this.faceitElo = faceitElo;
        this.level = level;
        this.region = region;
    }

    public Integer getFaceitElo() {
        return faceitElo;
    }

    public Integer getLevel() {
        return level;
    }

    public String getRegion() {
        return region;
    }
}
