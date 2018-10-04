package integration


import integration.common.WireMockRunner
import io.github.przbetkier.tuscan.TuscanApplication
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.AutoCleanup
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

    @AutoCleanup("deleteAll")
    @Autowired
    LatestProfileRepository latestProfileRepository

    @LocalServerPort
    protected int port

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }

    def setupSpec() {
        WireMockRunner.start()
    }

    void cleanup() {
        WireMockRunner.cleanupAll()
    }
}
