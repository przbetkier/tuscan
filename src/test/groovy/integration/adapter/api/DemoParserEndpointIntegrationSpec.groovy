package integration.adapter.api

import groovy.json.JsonSlurper
import integration.BaseIntegrationSpec
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType

class DemoParserEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should save demo stats"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource('__files/demoDetailsRequest.json').getFile())

        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parse(file)
        def headers = new HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        def entity = new HttpEntity(data, headers)

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/demo-parser"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 201
        demoStatsRepository.count().block() == 1L
    }
}
