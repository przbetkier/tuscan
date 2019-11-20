package io.github.przbetkier.tuscan.adapter.api.request;

import java.math.BigDecimal;

public class LatestProfileRequest {

    private final String nickname;
    private final String avatarUrl;
    private final Integer level;
    private final Integer elo;
    private final BigDecimal kdRatio;

    public LatestProfileRequest(String nickname, String avatarUrl, Integer level, Integer elo, BigDecimal kdRatio) {
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.level = level;
        this.elo = elo;
        this.kdRatio = kdRatio;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getElo() {
        return elo;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }
}
