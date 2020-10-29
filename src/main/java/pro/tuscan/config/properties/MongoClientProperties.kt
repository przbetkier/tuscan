package pro.tuscan.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("mongo-client-properties")
data class MongoClientProperties(val socketTimeout: Int,
                                 val connectionsPerHost: Int,
                                 val maxConnectionIdleTime: Long)
