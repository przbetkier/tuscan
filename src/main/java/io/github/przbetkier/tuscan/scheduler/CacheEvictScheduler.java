package io.github.przbetkier.tuscan.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CacheEvictScheduler.class);

    @CacheEvict(allEntries = true, cacheNames = {"player_history", "simple_matches", "detailed_matches",
            "player_details", "player_csgo_stats"})
    @Scheduled(fixedDelay = 120000)
    public void cacheEvict() {
        logger.info("Evicting cache from all results.");
    }
}
