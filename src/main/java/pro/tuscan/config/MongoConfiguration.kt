package pro.tuscan.config

import com.mongodb.MongoClientOptions
import com.mongodb.WriteConcern
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pro.tuscan.config.properties.MongoClientProperties

@Configuration
class MongoConfiguration(private val properties: MongoClientProperties) {

    @Bean
    fun mongoClientOptions(): MongoClientOptions =
            MongoClientOptions.builder()
                    .socketTimeout(properties.socketTimeout)
                    .connectionsPerHost(properties.connectionsPerHost)
                    .maxConnectionIdleTime(properties.maxConnectionIdleTime)
                    .writeConcern(WriteConcern.ACKNOWLEDGED)
                    .build()
}
