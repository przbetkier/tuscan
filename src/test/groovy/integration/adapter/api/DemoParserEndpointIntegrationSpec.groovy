package integration.adapter.api

import groovy.json.JsonSlurper
import integration.BaseIntegrationSpec
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod

import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class DemoParserEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should save demo stats and return response when requested"() {
        given:'incoming request from AWS Lambda demo parser'
        def data = sampleDemoDetailsRequest()
        def headers = new HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        def entity = new HttpEntity(data, headers)

        when:'is going to be saved'
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-stats"), HttpMethod.POST, entity, Map)

        then:'saved successfully'
        response.statusCodeValue == 201
        demoStatsRepository.count().block() == 1L
        def id = response.body.matchId

        when:'frontend ask for demo stats'
        def demoStats = restTemplate.getForEntity(localUrl("/tuscan-api/demo-stats/$id"), Map)

        then:'receives successful response'
        demoStats.statusCodeValue == 200
        demoStats.body.matchId == id
    }

    private def sampleDemoDetailsRequest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource('__files/demoDetailsRequest.json').getFile())

        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parse(file)
    }
}
