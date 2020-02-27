package pro.tuscan.client;

import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.tuscan.config.properties.FaceitWebClientProperties;
import pro.tuscan.exception.FaceitServerException;
import reactor.retry.Retry;

import java.time.Duration;

@Component
public class RetryInvoker {

    private final FaceitWebClientProperties properties;

    public RetryInvoker(FaceitWebClientProperties properties) {
        this.properties = properties;
    }

    private static final Logger logger = LoggerFactory.getLogger(RetryInvoker.class);

    public Retry<Object> defaultFaceitPolicy(String methodName) {
        return Retry.anyOf(FaceitServerException.class, ReadTimeoutException.class)
                .retryMax(properties.getRetry().getMaxRetries())
                .fixedBackoff(Duration.ofMillis(properties.getRetry().getBackoff()))
                .doOnRetry(r -> logger.warn("Exception [{}] occurred during request: [{}]. Retrying.",
                                            r.exception(),
                                            methodName));
    }

}
