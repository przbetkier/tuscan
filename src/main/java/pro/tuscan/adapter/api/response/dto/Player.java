package pro.tuscan.adapter.api.response.dto;

public class Player {

    private final String playerId;
    private final String nickname;
    private final PlayerStats playerStats;

    public Player(String playerId, String nickname, PlayerStats playerStats) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.playerStats = playerStats;
    }

    public String getPlayerId() {
        return playerId;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public String getNickname() {
        return nickname;
    }
}
