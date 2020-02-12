package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.client.match.MatchDemoDto

@CompileStatic
class SampleMatchDemoDto {

    public static final Collection<String> URLS = ["url-1"]

    static MatchDemoDto simple() {
        return new MatchDemoDto(URLS);
    }
}
