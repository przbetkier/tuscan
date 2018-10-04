package io.github.przbetkier.tuscan.config;

import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Bean
    MongoClientOptions mongoClientOptions()  {
        return MongoClientOptions.builder()
                .socketTimeout(1000)
                .connectionsPerHost(50)
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .build();
    }
}
