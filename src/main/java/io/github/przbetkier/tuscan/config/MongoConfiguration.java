package io.github.przbetkier.tuscan.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mongodb.WriteConcern.ACKNOWLEDGED;

@Configuration
public class MongoConfiguration {

    @Bean
    MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder()
                .socketTimeout(2000)
                .connectionsPerHost(50)
                .writeConcern(ACKNOWLEDGED)
                .build();
    }
}
