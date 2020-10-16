package pro.tuscan.client.player

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import pro.tuscan.client.FaceitClient
import pro.tuscan.client.RetryInvoker
import pro.tuscan.domain.player.PlayerHistoryMapper.map
import pro.tuscan.domain.player.exception.PlayerNotFoundException
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.Instant

@Component
class PlayerHistoryClient(@Qualifier("openFaceitClient") private val openFaceitClient: WebClient,
                          private val retryInvoker: RetryInvoker) : FaceitClient() {

    fun getPlayerHistory(playerId: String): Mono<PlayerHistoryResponse> {
        return openFaceitClient.method(GET)
                .uri("/stats/api/v1/stats/time/users/{playerId}/games/csgo", playerId)
                .retrieve()
                .onStatus({ status -> status.is4xxClientError }, { throwClientException(playerId) })
                .onStatus({ status -> status.is5xxServerError }, { response -> throwServerException(response.rawStatusCode()) })
                .bodyToMono<List<MatchHistoryDto>>(object : ParameterizedTypeReference<List<MatchHistoryDto>>() {})
                .name("playerHistory")
                .metrics()
                .map { historyMatches: List<MatchHistoryDto> -> map(historyMatches) }
                .retryWhen(retryInvoker.defaultFaceitPolicy("playerHistory"))
    }

    private fun throwClientException(playerId: String): Mono<PlayerNotFoundException> {
        throw PlayerNotFoundException("Player [$playerId] history could not be found on Faceit.")
    }
}

data class PlayerHistoryResponse(val matchHistory: List<MatchHistory>)

data class MatchHistory(val matchId: String,
                        val date: Instant,
                        val elo: Int,
                        val eloDiff: Int,
                        val kdRatio: BigDecimal,
                        val hsPercentage: Int)
