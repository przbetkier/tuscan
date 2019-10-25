package io.github.przbetkier.tuscan.domain.profiles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "latestProfiles")
public class LatestProfile {

    @Id
    private String nickname;

    private String avatarUrl;
    private Integer level;
    private Integer elo;
    private BigDecimal kdRatio;
    private Instant createdOn;

    public LatestProfile(String nickname, String avatarUrl, Integer level, Integer elo, BigDecimal kdRatio,
                         Instant createdOn) {
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.level = level;
        this.elo = elo;
        this.kdRatio = kdRatio;
        this.createdOn = createdOn;
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

    public Instant getCreatedOn() {
        return createdOn;
    }
}
