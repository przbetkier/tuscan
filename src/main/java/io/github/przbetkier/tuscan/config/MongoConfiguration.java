package io.github.przbetkier.tuscan.config;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mongodb.WriteConcern.ACKNOWLEDGED;

@Configuration
public class MongoConfiguration {

    @Bean
    MongoClientOptions mongoClientOptions(@Value("${mongoClientProperties.socketTimeout}") Integer socketTimeout,
                                          @Value("${mongoClientProperties.connectionsPerHost}") Integer connectionsPerHost) {
        return MongoClientOptions.builder()
                .socketTimeout(socketTimeout)
                .connectionsPerHost(connectionsPerHost)
                .writeConcern(ACKNOWLEDGED)
                .build();
    }
}
