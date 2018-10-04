package io.github.przbetkier.tuscan.domain.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {

    private String position;

    @JsonCreator
    public Position(@JsonProperty("position") String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
