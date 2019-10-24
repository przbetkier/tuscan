package io.github.przbetkier.tuscan.common

import groovy.transform.CompileStatic
import io.github.przbetkier.tuscan.client.match.MatchDemoDto

@CompileStatic
class SampleMatchDemoDto {

    public static final Collection<String> URLS = ["url-1"]

    static MatchDemoDto simple() {
        return new MatchDemoDto(URLS);
    }
}
