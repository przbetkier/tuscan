package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails;

public class PlayerDetailsResponse {

    private final String playerId;
    private final String nickname;
    private final GameDetails gameDetails;
    private final String avatarUrl;

    public PlayerDetailsResponse(String playerId, String nickname, GameDetails gameDetails, String avatarUrl) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.gameDetails = gameDetails;
        this.avatarUrl = avatarUrl;
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
}
