package io.github.przbetkier.tuscan.config.properties;

import javax.validation.constraints.Min;
import java.time.Duration;

public class Retry {

    @Min(1)
    private long maxBackoff;

    @Min(1)
    private long minBackoff;

    @Min(1)
    private long maxRetries;

    public Duration getMax() {
        return Duration.ofMillis(maxBackoff);
    }

    public void setMaxBackoff(int maxBackoff) {
        this.maxBackoff = maxBackoff;
    }

    public Duration getMin() {
        return Duration.ofMillis(minBackoff);
    }

    public void setMinBackoff(int minBackoff) {
        this.minBackoff = minBackoff;
    }

    public long getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(long maxRetries) {
        this.maxRetries = maxRetries;
    }
}
