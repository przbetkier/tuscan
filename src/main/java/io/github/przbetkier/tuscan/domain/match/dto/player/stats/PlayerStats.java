package io.github.przbetkier.tuscan.domain.match.dto.player.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerStats {

    private Lifetime lifetime;
    private List<Segment> segments;

    @JsonCreator
    public PlayerStats(@JsonProperty("lifetime") Lifetime lifetime,
                       @JsonProperty("segments") List<Segment> segments) {
        this.lifetime = lifetime;
        this.segments = segments;
    }

    public Lifetime getLifetime() {
        return lifetime;
    }

    public void setLifetime(Lifetime lifetime) {
        this.lifetime = lifetime;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
