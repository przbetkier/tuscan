package integration.adapter.api

import groovy.json.JsonSlurper
import integration.BaseIntegrationSpec
import io.github.przbetkier.tuscan.common.SampleMatch
import io.github.przbetkier.tuscan.domain.match.DemoStatus
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import spock.util.concurrent.PollingConditions

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
}
