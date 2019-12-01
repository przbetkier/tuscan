package io.github.przbetkier.tuscan.client.lambda;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LambdaDemoParserClient {

    private final WebClient lambdaClient;

    public LambdaDemoParserClient(@Qualifier("awsLambdaClient") WebClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    public Mono<LambdaResponse> invokeLambda(LambdaRequest lambdaRequest) {
        return lambdaClient.method(HttpMethod.POST)
                .uri("/default/demo-parser")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(lambdaRequest))
                .retrieve()
                .bodyToMono(LambdaResponse.class);
    }
}
