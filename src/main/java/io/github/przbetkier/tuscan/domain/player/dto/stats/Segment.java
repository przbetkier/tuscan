package io.github.przbetkier.tuscan.domain.player.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Segment {

    private String name;
    private MapStatistics mapStatistics;

    @JsonCreator
    public Segment(@JsonProperty("label") String name,
                   @JsonProperty("stats") MapStatistics mapStatistics) {
        this.name = name;
        this.mapStatistics = mapStatistics;
    }

    public String getName() {
        return name;
    }

    public MapStatistics getMapStatistics() {
        return mapStatistics;
    }
}
