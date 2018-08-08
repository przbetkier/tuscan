package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class GameDetails {

    private final Integer faceitElo;
    private final Integer level;

    public GameDetails(Integer faceitElo, Integer level) {
        this.faceitElo = faceitElo;
        this.level = level;
    }

    public Integer getFaceitElo() {
        return faceitElo;
    }

    public Integer getLevel() {
        return level;
    }
}
