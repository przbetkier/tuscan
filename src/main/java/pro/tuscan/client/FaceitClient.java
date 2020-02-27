package pro.tuscan.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tuscan.exception.FaceitServerException;
import reactor.core.publisher.Mono;

public abstract class FaceitClient {

    private static final Logger logger = LoggerFactory.getLogger(FaceitClient.class);

    public static Mono<FaceitServerException> throwServerException(int statusCode) {
        logger.warn("Faceit request failed [{}} error].", statusCode);
        throw new FaceitServerException("Faceit server error occurred.");
    }
}
