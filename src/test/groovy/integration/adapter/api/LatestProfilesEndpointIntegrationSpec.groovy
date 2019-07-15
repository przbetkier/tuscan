package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.LatestProfileSample
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfile

class LatestProfilesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return four latest profiles ordered by date"() {
        given:
        def latestProfiles = generateLatestProfiles(4)
        latestProfileRepository.saveAll(latestProfiles).blockLast()

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/latest-profiles"), List)

        then:
        response.statusCodeValue == 200
        response.body.size() == 4
    }

    static def generateLatestProfiles(int count) {
        def latestProfiles = []
        count.times {
            latestProfiles.add(LatestProfileSample.simple("nickname-$it"))
        }
        return latestProfiles
    }
}
