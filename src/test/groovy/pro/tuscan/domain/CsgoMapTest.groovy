package pro.tuscan.domain

import spock.lang.Specification
import spock.lang.Unroll

class CsgoMapTest extends Specification {

    @Unroll
    def "should return #isInMapPool for map #mapName when checking active map pool"() {
        given:
        def name = mapName

        when:
        def result = CsgoMap.isInMapPool(name)

        then:
        result == isInMapPool

        where:
        mapName       | isInMapPool
        "de_inferno"  | true
        "de_mirage"   | true
        "de_cache"    | true
        "de_train"    | true
        "de_dust2"    | true
        "de_overpass" | true
        "de_nuke"     | true
        "de_vertigo"  | true
        "de_season"   | false
        "de_tuscan"   | false
        "de_cbble"    | false
        "aim_map"     | false
        "cs_militia"  | false
    }
}
