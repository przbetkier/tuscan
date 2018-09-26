package io.github.przbetkier.tuscan.domain

import spock.lang.Specification
import spock.lang.Unroll

class CsgoMapTest extends Specification {

    @Unroll
    def "should return #result for map #mapName when checking active map pool"() {
        given:
        def name = mapName

        when:
        def isInMapPool = CsgoMap.isInMapPool(name)

        then:
        isInMapPool == result

        where:
        mapName       | result
        "de_inferno"  | true
        "de_mirage"   | true
        "de_cache"    | true
        "de_cbble"    | true
        "de_train"    | true
        "de_dust2"    | true
        "de_season"   | true
        "de_overpass" | true
        "de_nuke"     | true
        "de_tuscan"   | false
        "aim_map"     | false
        "cs_militia"  | false
    }
}
