package pro.tuscan.client.lambda

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpMethod.POST
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.client.WebClient
import pro.tuscan.adapter.api.LambdaRequest
import pro.tuscan.adapter.api.LambdaResponse
import reactor.core.publisher.Mono

@Component
class LambdaDemoParserClient(@Qualifier("awsLambdaClient") private val lambdaClient: WebClient) {

    fun invokeLambda(lambdaRequest: LambdaRequest): Mono<LambdaResponse> =
            lambdaClient.method(POST)
                    .uri("/default/demo-parser")
                    .contentType(APPLICATION_JSON)
                    .body(fromValue(lambdaRequest))
                    .retrieve()
                    .bodyToMono(LambdaResponse::class.java)
}

