package integration.common.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class PlayerBanStubs {

    static def stubSuccessfulResponse(String playerId, String reason, String endsAt) {
        stubFor(get(urlEqualTo("/sheriff/v1/bans/$playerId"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(toBody(playerId, reason, endsAt))))
    }

    private static def toBody(String playerId, String reason, String endsAt) {
        """
        {
            "code": "OPERATION-OK",
            "env": "prod",
            "message": "Operation performed correctly.",
            "payload": [
                {
                    "nickname": "BannedPlayer",
                    "type": "login",
                    "reason": "$reason",
                    "game": null,
                    "starts_at": "2018-11-10T18:54:39.390Z",
                    "ends_at": $endsAt,
                    "user_id": "$playerId"
                }
        ],
            "time": 1602330845355,
            "version": "1.35.7"
        }
        """
    }
}
