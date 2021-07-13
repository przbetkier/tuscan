package integration

import integration.common.WireMockRunner
import org.junit.ClassRule
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import pro.tuscan.TuscanApplication
import pro.tuscan.domain.match.MatchRepository
import pro.tuscan.domain.profiles.LatestProfileRepository
import pro.tuscan.domain.stats.DemoStatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
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
    LatestProfileRepository latestProfileRepository

    @Autowired
    MatchRepository matchRepository

    @Autowired
    DemoStatsRepository demoStatsRepository

    @LocalServerPort
    protected int port

    private static final String MONGO_DB_DOCKER_IMAGE_NAME = "mongo:4.0.13"
    private static final int MONGO_DB_EXPOSED_CONTAINER_PORT = 27017

    @Shared
    @ClassRule
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse(MONGO_DB_DOCKER_IMAGE_NAME)
                    .asCompatibleSubstituteFor("mongo"))

    def setupSpec() {
        WireMockRunner.start()
        mongoDBContainer.start()
    }

    def cleanup() {
        WireMockRunner.cleanupAll()
        clearRepositories()
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", { mongoDBContainer.getContainerIpAddress() })
        registry.add("spring.data.mongodb.port", { mongoDBContainer.getMappedPort(MONGO_DB_EXPOSED_CONTAINER_PORT) })
    }

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }

    def clearRepositories() {
        latestProfileRepository.deleteAll().block()
        matchRepository.deleteAll().block()
        demoStatsRepository.deleteAll().block()
    }
}
