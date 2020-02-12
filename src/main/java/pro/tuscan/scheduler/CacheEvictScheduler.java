package pro.tuscan.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod", "local"})
class CacheEvictScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CacheEvictScheduler.class);
    private static final long FIVE_MINUTES = 300_000L;

    @CacheEvict(allEntries = true, cacheNames = {"playerHistory",
            "playerDetails", "playerCsgoStats", "playerPosition", "playerMatch"})
    @Scheduled(fixedDelay = FIVE_MINUTES)
    public void cacheEvict() {
        logger.debug("Evicting cache from all results.");
    }
}
