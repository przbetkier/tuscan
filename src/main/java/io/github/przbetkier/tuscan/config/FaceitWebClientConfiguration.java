package io.github.przbetkier.tuscan.config;

import io.github.przbetkier.tuscan.config.properties.FaceitWebClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class FaceitWebClientConfiguration {

    private final FaceitWebClientProperties faceitWebClientProperties;

    FaceitWebClientConfiguration(FaceitWebClientProperties faceitWebClientProperties) {
        this.faceitWebClientProperties = faceitWebClientProperties;
    }

    @Bean
    WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(faceitWebClientProperties.getUrl())
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + faceitWebClientProperties.getApiKey())
                .build();
    }
}
