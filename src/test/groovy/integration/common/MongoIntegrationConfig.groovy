package integration.common

import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.SimpleMongoDbFactory

@TestConfiguration
class MongoIntegrationConfig {

    def static final DB_NAME = "InMemoryMongo"

    @Bean(destroyMethod = "close")
    MongoClient mongo() {
        MongodForTestsFactory
                .with(Version.Main.V3_6)
                .newMongo()
    }

    @Bean
    MongoDbFactory mongoDbFactory(MongoClient client) {
        new SimpleMongoDbFactory(client, DB_NAME)
    }
}