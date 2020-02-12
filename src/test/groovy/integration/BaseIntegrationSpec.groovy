package integration

import integration.common.WireMockRunner
import pro.tuscan.TuscanApplication
import pro.tuscan.domain.match.MatchRepository
import pro.tuscan.domain.profiles.LatestProfileRepository
import pro.tuscan.domain.stats.DemoStatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cache.CacheManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(
        classes = TuscanApplication,
        properties = "application.environment=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class BaseIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    CacheManager cacheManager

    @Autowired
    LatestProfileRepository latestProfileRepository

    @Autowired
    MatchRepository matchRepository

    @Autowired
    DemoStatsRepository demoStatsRepository

    @LocalServerPort
    protected int port

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }

    def setupSpec() {
        WireMockRunner.start()
    }

    def cleanup() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear()
        }
        WireMockRunner.cleanupAll()
        clearRepositories()
    }

    def clearRepositories() {
        latestProfileRepository.deleteAll().subscribe()
        matchRepository.deleteAll().subscribe()
        demoStatsRepository.deleteAll().subscribe()
    }
}
