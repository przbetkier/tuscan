package pro.tuscan.config.properties;

import javax.validation.constraints.Min;
import java.time.Duration;

public class Retry {

    @Min(1)
    private long backoff;

    @Min(1)
    private long maxRetries;

    public long getBackoff() {
        return backoff;
    }

    public void setBackoff(long backoff) {
        this.backoff = backoff;
    }

    public long getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(long maxRetries) {
        this.maxRetries = maxRetries;
    }
}
