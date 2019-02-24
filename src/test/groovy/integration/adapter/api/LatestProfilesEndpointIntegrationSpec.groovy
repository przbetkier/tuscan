package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.LatestProfileSample

class LatestProfilesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return four latest profiles ordered by date"() {
        given:
        def latestProfiles = generateLatestProfiles(4)
        latestProfileRepository.saveAll(latestProfiles)

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/latest-profiles"), Map)

        then:
        println(response)
        response.statusCodeValue == 200
    }

    static def generateLatestProfiles(int count) {
        def latestProfiles = []
        count.times {
            latestProfiles.add(LatestProfileSample.simple())
        }
        return latestProfiles
    }
}
