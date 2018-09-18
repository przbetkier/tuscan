package io.github.przbetkier.tuscan.domain.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Games {

    private Csgo csgo;

    @JsonCreator
    public Games(@JsonProperty("csgo") Csgo csgo) {
        this.csgo = csgo;
    }

    public Csgo getCsgo() {
        return csgo;
    }

    public void setCsgo(Csgo csgo) {
        this.csgo = csgo;
    }
}