package io.github.przbetkier.tuscan.domain.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Csgo {

    private Integer faceitElo;
    private Integer level;
    private String region;

    @JsonCreator
    public Csgo(@JsonProperty("faceit_elo") Integer faceitElo,
                @JsonProperty("skill_level") Integer level,
                @JsonProperty("region") String region) {
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
