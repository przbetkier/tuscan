package pro.tuscan.config

import com.mongodb.MongoClientSettings
import com.mongodb.WriteConcern.ACKNOWLEDGED
import com.mongodb.connection.ConnectionPoolSettings
import com.mongodb.connection.SocketSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pro.tuscan.config.properties.MongoClientProperties
import java.util.concurrent.TimeUnit.MILLISECONDS

@Configuration
class MongoConfiguration(private val properties: MongoClientProperties) {

    @Bean
    fun mongoClientOptions(): MongoClientSettings =
            MongoClientSettings.builder()
                    .applyToSocketSettings {
                        SocketSettings.builder()
                                .connectTimeout(properties.socketTimeout, MILLISECONDS)
                                .readTimeout(properties.socketTimeout, MILLISECONDS)
                                .build()
                    }
                    .applyToConnectionPoolSettings {
                        ConnectionPoolSettings.builder()
                                .maxConnectionIdleTime(properties.maxConnectionIdleTime, MILLISECONDS)
                                .maxSize(properties.connectionsPerHost)
                                .build()
                    }
                    .writeConcern(ACKNOWLEDGED)
                    .build()
}
