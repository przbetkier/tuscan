package io.github.przbetkier.tuscan.adapter.api.response;

public class DetailedPlayerCsgoStatsResponse extends PlayerCsgoStatsResponse {

    private String nickname;

    // Essential for Jackson
    public DetailedPlayerCsgoStatsResponse() {
    }

    private DetailedPlayerCsgoStatsResponse(PlayerCsgoStatsResponse response, String nickname) {
        super(response.getOverallStats(), response.getMapStats());
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static DetailedPlayerCsgoStatsResponse fromPlayerCsgoStatsResponse(PlayerCsgoStatsResponse response,
                                                                              String nickname) {
        return new DetailedPlayerCsgoStatsResponse(response, nickname);
    }
}
