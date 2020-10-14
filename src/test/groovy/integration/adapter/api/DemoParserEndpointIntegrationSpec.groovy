package integration.adapter.api

import com.github.tomakehurst.wiremock.client.WireMock
import groovy.json.JsonSlurper
import integration.BaseIntegrationSpec
import integration.common.stubs.LambdaStubs
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import pro.tuscan.adapter.api.LambdaInvokerRequest
import pro.tuscan.common.SampleMatch
import pro.tuscan.domain.match.DemoStatus
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import static com.github.tomakehurst.wiremock.client.WireMock.exactly
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class DemoParserEndpointIntegrationSpec extends BaseIntegrationSpec {

    PollingConditions conditions = new PollingConditions()

    def "should save demo stats and return response when requested"() {
        given: 'incoming request from AWS Lambda demo parser for already saved match'
        def match = SampleMatch.simple()
        matchRepository.save(match).subscribe()

        def data = sampleDemoDetailsRequest()
        def headers = new HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        def entity = new HttpEntity(data, headers)

        when: 'is going to be saved'
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats"), HttpMethod.POST, entity, Map)

        then: 'saved successfully and updates match demo status'
        response.statusCodeValue == 201
        demoStatsRepository.count().block() == 1L
        response.body.matchId == match.matchId

        conditions.eventually {
            def savedMatch = matchRepository.findById(match.matchId)
            savedMatch.block().demoStatus == DemoStatus.PARSED
        }

        when: 'frontend ask for demo stats'
        def demoStats = restTemplate.getForEntity(localUrl("/tuscan-api/demo-stats/$match.matchId"), Map)

        then: 'receives successful response'
        demoStats.statusCodeValue == 200
        demoStats.body.matchId == match.matchId
    }

    def "should change demo status for COMPUTING for valid lambda request"() {
        given:
        def match = SampleMatch.simple()
        matchRepository.save(match).subscribe()
        def entity = lambdaRequestForMatchId(match.matchId)
        LambdaStubs.stubSuccessfulResponse()

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats/invoke"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 201
        matchRepository.findById(match.matchId).block().demoStatus == DemoStatus.COMPUTING
    }

    @Unroll
    def "should return #expectedStatus for demo status #status if match exists"() {
        given:
        def match = SampleMatch.simple(matchId, status)
        matchRepository.save(match).subscribe()
        def entity = lambdaRequestForMatchId(match.matchId)
        LambdaStubs.stubSuccessfulResponse()

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats/invoke"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == expectedStatus
        conditions.eventually {
            WireMock.verify(exactly(lambdaInvocations), postRequestedFor(urlMatching("/default/demo-parser")))
        }

        where:
        matchId   | status               | expectedStatus | lambdaInvocations
        "matchId" | DemoStatus.COMPUTING | 422            | 0
        "matchId" | DemoStatus.PARSED    | 422            | 0
        "matchId" | DemoStatus.NO_ACTION | 201            | 1
    }

    def "should return 404 for lambda request if match does not exist"() {
        given:
        def entity = lambdaRequestForMatchId(UUID.randomUUID().toString())
        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats/invoke"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 404
        WireMock.verify(exactly(0), postRequestedFor(urlMatching("/default/demo-parser")))
    }

    def "should revert match to no-action status if lambda failed"() {
        given:
        LambdaStubs.stubExceptionalResponse()
        def match = SampleMatch.simple()
        matchRepository.save(match).subscribe()
        def entity = lambdaRequestForMatchId(match.matchId)

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats/invoke"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 500
        WireMock.verify(exactly(1), postRequestedFor(urlMatching("/default/demo-parser")))
        conditions.eventually {
            matchRepository.findById(match.matchId).block().demoStatus == DemoStatus.NO_ACTION
        }
    }

    def "should return 404 not found when there is no stats for given matchId"() {
        given:
        def matchId = UUID.randomUUID().toString()

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/demo-stats/$matchId"), Map)

        then:
        response.statusCodeValue == 404
    }

    private def sampleDemoDetailsRequest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource('__files/demoDetailsRequest.json').getFile())

        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parse(file)
    }

    private static HttpEntity lambdaRequestForMatchId(String matchId) {
        def data = new LambdaInvokerRequest(matchId)
        def headers = new HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        return new HttpEntity(data, headers)
    }
}
