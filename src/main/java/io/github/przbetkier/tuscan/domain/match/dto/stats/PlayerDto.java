package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerDto {

    private String playerId;
    private String nickname;
    private PlayerStatsDto playerStats;

    @JsonCreator
    public PlayerDto(@JsonProperty("player_id") String playerId,
                     @JsonProperty("nickname") String nickname,
                     @JsonProperty("player_stats") PlayerStatsDto playerStats) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.playerStats = playerStats;
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

    public PlayerStatsDto getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStatsDto playerStats) {
        this.playerStats = playerStats;
    }
}
