package pro.tuscan.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun kotlinModule() = KotlinModule()

    @Bean
    fun javaTimeModule() = JavaTimeModule()

    @Bean
    fun afterburnerModule() = AfterburnerModule()

    @Bean("tuscanObjectMapper")
    fun objectMapper(): ObjectMapper =
            ObjectMapper()
                    .registerModule(kotlinModule())
                    .registerModule(javaTimeModule())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @Bean
    fun jackson2JsonEncoder(@Qualifier("tuscanObjectMapper") mapper: ObjectMapper) =
            Jackson2JsonEncoder(mapper)

    @Bean
    fun jackson2JsonDecoder(@Qualifier("tuscanObjectMapper") mapper: ObjectMapper) =
            Jackson2JsonDecoder(mapper)

    @Bean
    fun webFluxConfigurer(encoder: Jackson2JsonEncoder, decoder: Jackson2JsonDecoder) =
            object : WebFluxConfigurer {
                override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                    configurer.defaultCodecs().jackson2JsonEncoder(encoder)
                    configurer.defaultCodecs().jackson2JsonDecoder(decoder)
                }
            }
}
