package pro.tuscan.config;

import pro.tuscan.config.properties.AWSLambdaParserProperties;
import pro.tuscan.config.properties.FaceitWebClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
class FaceitWebClientConfiguration {

    private final FaceitWebClientProperties faceitWebClientProperties;
    private final AWSLambdaParserProperties awsLambdaParserProperties;

    FaceitWebClientConfiguration(FaceitWebClientProperties faceitWebClientProperties,
                                 AWSLambdaParserProperties awsLambdaParserProperties) {
        this.faceitWebClientProperties = faceitWebClientProperties;
        this.awsLambdaParserProperties = awsLambdaParserProperties;
    }

    @Bean(name = "faceitClient")
    WebClient faceitClient() {
        return WebClient.builder()
                .baseUrl(faceitWebClientProperties.getUrl())
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .defaultHeader(AUTHORIZATION, "Bearer " + faceitWebClientProperties.getApiKey())
                .build();
    }

    @Bean(name = "openFaceitClient")
    WebClient openFaceitClient() {
        return WebClient.builder()
                .baseUrl(faceitWebClientProperties.getOpenUrl())
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "awsLambdaClient")
    WebClient awsLambdaClient() {
        return WebClient.builder()
                .baseUrl(awsLambdaParserProperties.getEndpoint())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}
