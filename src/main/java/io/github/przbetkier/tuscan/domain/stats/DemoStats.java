package io.github.przbetkier.tuscan.domain.stats;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "demoStats")
public class DemoStats {

    @Id
    private String matchId;

    private Instant createdOn;
    private List<PlayerDemoStats> stats;

    public DemoStats(String matchId, Instant createdOn, List<PlayerDemoStats> stats) {
        this.matchId = matchId;
        this.createdOn = createdOn;
        this.stats = stats;
    }

    public String getMatchId() {
        return matchId;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public List<PlayerDemoStats> getStats() {
        return stats;
    }
}
