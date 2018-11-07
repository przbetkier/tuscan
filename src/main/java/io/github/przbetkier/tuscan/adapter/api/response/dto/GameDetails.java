package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class GameDetails {

    private final Integer faceitElo;
    private final Integer level;
    private final String region;
    private final String steamId;


    public GameDetails(Integer faceitElo, Integer level, String region, String steamId) {
        this.faceitElo = faceitElo;
        this.level = level;
        this.region = region;
        this.steamId = steamId;
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

    public String getSteamId() {
        return steamId;
    }
}
