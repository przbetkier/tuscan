package pro.tuscan.client.player

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Instant

@Component
class PlayerBanClient(@Qualifier("openFaceitClient") private val openFaceitClient: WebClient) {

    fun getPlayerBanInfo(playerId: String): Mono<BanInfoResponse> =
            openFaceitClient.method(GET)
                    .uri { uriBuilder -> uriBuilder.path("/sheriff/v1/bans/{playerId}").build(playerId) }
                    .retrieve()
                    .bodyToMono(BanInfo::class.java)
                    .name("bans")
                    .metrics()
                    .map { banInfo ->
                        BanInfoResponse(banInfo.payload.map {
                            Ban(it.reason, it.startsAt, it.endsAt)
                        })
                    }
}

data class BanInfo(val payload: List<BanInfoPayload>)

data class BanInfoPayload(val reason: String,
                          @JsonProperty("starts_at") val startsAt: Instant,
                          @JsonProperty("ends_at") val endsAt: Instant?)

data class BanInfoResponse(val bans: List<Ban>)

data class Ban(val reason: String,
               val startsAt: Instant,
               val endsAt: Instant?)
