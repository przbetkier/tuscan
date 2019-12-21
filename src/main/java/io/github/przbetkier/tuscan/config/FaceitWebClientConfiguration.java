package io.github.przbetkier.tuscan.config;

import io.github.przbetkier.tuscan.config.properties.AWSLambdaParserProperties;
import io.github.przbetkier.tuscan.config.properties.FaceitWebClientProperties;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
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
                .clientConnector(defaultFaceitHttpConnector())
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

    private ReactorClientHttpConnector defaultFaceitHttpConnector() {
        HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient -> {
            tcpClient = tcpClient.option(CONNECT_TIMEOUT_MILLIS, faceitWebClientProperties.getTimeout().getConnect());
            tcpClient = tcpClient.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(
                    faceitWebClientProperties.getTimeout().getRead(),
                    TimeUnit.MILLISECONDS)));
            return tcpClient;
        });
        return new ReactorClientHttpConnector(httpClient);
    }
}
