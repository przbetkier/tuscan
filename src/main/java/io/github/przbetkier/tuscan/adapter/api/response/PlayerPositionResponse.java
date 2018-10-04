package io.github.przbetkier.tuscan.adapter.api.response;

public class PlayerPositionResponse {

    private final String playerId;
    private final Integer positionInRegion;
    private final Integer positionInCountry;

    public PlayerPositionResponse(String playerId, Integer positionInRegion, Integer positionInCountry) {
        this.playerId = playerId;
        this.positionInRegion = positionInRegion;
        this.positionInCountry = positionInCountry;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Integer getPositionInRegion() {
        return positionInRegion;
    }

    public Integer getPositionInCountry() {
        return positionInCountry;
    }
}
