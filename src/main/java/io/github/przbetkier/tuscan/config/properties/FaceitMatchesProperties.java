package io.github.przbetkier.tuscan.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "faceit-matches")
public class FaceitMatchesProperties {

    private Integer cutoffDateTimestamp;

    public Integer getCutoffDateTimestamp() {
        return cutoffDateTimestamp;
    }

    public void setCutoffDateTimestamp(Integer cutoffDateTimestamp) {
        this.cutoffDateTimestamp = cutoffDateTimestamp;
    }
}
