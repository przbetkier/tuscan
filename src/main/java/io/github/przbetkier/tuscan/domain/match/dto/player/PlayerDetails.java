package io.github.przbetkier.tuscan.domain.match.dto.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean hasCsgoGame() {
        return this.getGames().getCsgo() != null;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

