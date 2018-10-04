package integration.common

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

class WireMockRunner {
    private static int wireMockPort = 10200
    private static WireMockServer wireMockServer = new WireMockServer(wireMockPort)

    static def start() {
        if (!wireMockServer.running) {
            wireMockServer.start()
            WireMock.configureFor(wireMockServer.port())
        }
    }

    static def cleanupAll() {
        wireMockServer.resetAll()
    }
}