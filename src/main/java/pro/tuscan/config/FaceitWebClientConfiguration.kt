package pro.tuscan.config

import io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import pro.tuscan.config.properties.AwsLambdaParserProperties
import pro.tuscan.config.properties.FaceitWebClientProperties
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit.MILLISECONDS

@Configuration
internal class FaceitWebClientConfiguration(private val faceitWebClientProperties: FaceitWebClientProperties,
                                            private val awsLambdaParserProperties: AwsLambdaParserProperties) {
    @Bean(name = ["faceitClient"])
    fun faceitClient(): WebClient {
        val httpClient = defaultHttpClient(faceitWebClientProperties)
        return WebClient.builder()
                .clientConnector(ReactorClientHttpConnector(httpClient))
                .baseUrl(faceitWebClientProperties.url)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + faceitWebClientProperties.apiKey)
                .build()
    }

    @Bean(name = ["openFaceitClient"])
    fun openFaceitClient(): WebClient {
        return WebClient.builder()
                .baseUrl(faceitWebClientProperties.openUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }

    @Bean(name = ["awsLambdaClient"])
    fun awsLambdaClient(): WebClient {
        return WebClient.builder()
                .baseUrl(awsLambdaParserProperties.endpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }

    companion object {
        private fun defaultHttpClient(faceitWebClientProperties: FaceitWebClientProperties): HttpClient =
                TcpClient.create()
                        .option(CONNECT_TIMEOUT_MILLIS, faceitWebClientProperties.timeout.connect.toInt())
                        .doOnConnected { conn: Connection ->
                            conn.addHandlerLast(ReadTimeoutHandler(faceitWebClientProperties.timeout.read, MILLISECONDS))
                        }
                        .let { HttpClient.from(it) }
    }
}



