package io.github.przbetkier.tuscan.domain.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.przbetkier.tuscan.domain.player.dto.Games;

public class PlayerDetails {

    private String playerId;
    private String nickname;
    private Games games;
    private String avatarUrl;
    private String country;

    @JsonCreator
    public PlayerDetails(@JsonProperty("player_id") String playerId,
                         @JsonProperty("nickname") String nickname,
                         @JsonProperty("games") Games games,
                         @JsonProperty("avatar") String avatarUrl,
                         @JsonProperty("country") String country) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.games = games;
        this.avatarUrl = avatarUrl;
        this.country = country;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public Games getGames() {
        return games;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCountry() {
        return country;
    }

    boolean hasCsgoGame() {
        return this.getGames().getCsgo() != null;
    }
}

