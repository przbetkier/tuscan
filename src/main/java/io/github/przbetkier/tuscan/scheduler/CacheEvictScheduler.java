package io.github.przbetkier.tuscan.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class CacheEvictScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CacheEvictScheduler.class);
    private static final long FIVE_MINUTES = 300_000L;

    @CacheEvict(allEntries = true, cacheNames = {"player_history", "simple_matches", "detailed_matches",
            "player_details", "player_csgo_stats"})
    @Scheduled(fixedDelay = FIVE_MINUTES)
    public void cacheEvict() {
        logger.debug("Evicting cache from all results.");
    }
}
