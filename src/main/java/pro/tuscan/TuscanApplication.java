package pro.tuscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableReactiveMongoRepositories
@ConfigurationPropertiesScan
public class TuscanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuscanApplication.class, args);
	}
}
