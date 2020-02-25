package pro.tuscan.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.tuscan.config.properties.MongoClientProperties;

import static com.mongodb.WriteConcern.ACKNOWLEDGED;

@Configuration
public class MongoConfiguration {

    private final MongoClientProperties properties;

    public MongoConfiguration(MongoClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder()
                .socketTimeout(properties.getSocketTimeout())
                .connectionsPerHost(properties.getConnectionsPerHost())
                .maxConnectionIdleTime(properties.getMaxConnectionIdleTime())
                .writeConcern(ACKNOWLEDGED)
                .build();
    }
}
