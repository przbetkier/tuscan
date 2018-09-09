package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails;

public class PlayerDetailsResponse {

    private final String playerId;
    private final String nickname;
    private final GameDetails gameDetails;
    private final String avatarUrl;
    private final String country;

    public PlayerDetailsResponse(String playerId, String nickname, GameDetails gameDetails, String avatarUrl, String country) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.gameDetails = gameDetails;
        this.avatarUrl = avatarUrl;
        this.country = country;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCountry() {
        return country;
    }
}
