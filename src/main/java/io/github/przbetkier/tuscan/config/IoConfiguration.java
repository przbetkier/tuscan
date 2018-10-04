package io.github.przbetkier.tuscan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class IoConfiguration {

        @Bean
        public TaskExecutor threadPoolTaskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(300);
            executor.setMaxPoolSize(2000);
            executor.setThreadNamePrefix("io-");
            executor.initialize();
            return executor;
    }
}
