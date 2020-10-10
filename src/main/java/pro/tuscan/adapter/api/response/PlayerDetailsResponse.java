package pro.tuscan.adapter.api.response;

import pro.tuscan.adapter.api.response.dto.GameDetails;
import pro.tuscan.client.player.Membership;

public class PlayerDetailsResponse {

    private final String playerId;
    private final String nickname;
    private final GameDetails gameDetails;
    private final String avatarUrl;
    private final String country;
    private final Membership membership;

    public PlayerDetailsResponse(String playerId, String nickname, GameDetails gameDetails, String avatarUrl,
                                 String country, Membership membership) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.gameDetails = gameDetails;
        this.avatarUrl = avatarUrl;
        this.country = country;
        this.membership = membership;
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

    public Membership getMembership() {
        return membership;
    }
}
