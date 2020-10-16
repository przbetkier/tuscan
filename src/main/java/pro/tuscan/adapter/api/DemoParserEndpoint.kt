package pro.tuscan.adapter.api

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.request.DemoStatsDto
import pro.tuscan.domain.stats.DemoStats
import pro.tuscan.domain.stats.DemoStatsService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/tuscan-api/demo-stats")
class DemoParserEndpoint(private val demoStatsService: DemoStatsService) {

    private val logger = LoggerFactory.getLogger(DemoParserEndpoint::class.java)

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody demoDetailsRequest: DemoStatsDto): Mono<DemoStats> =
            demoStatsService.save(demoDetailsRequest)

    @GetMapping("/{matchId}")
    fun get(@PathVariable matchId: String): Mono<DemoStatsDto> =
            demoStatsService.getByMatchId(matchId)

    @PostMapping("/invoke")
    @ResponseStatus(CREATED)
    fun invoke(@RequestBody request: LambdaInvokerRequest): Mono<LambdaResponse> {
        logger.info("Requested lambda invocation for match [${request.matchId}].")
        return demoStatsService.invokeLambda(request.matchId)
    }
}

data class LambdaInvokerRequest(val matchId: String)

data class LambdaResponse(val message: String)

data class LambdaRequest(val matchId: String, val demoUrl: String?)
