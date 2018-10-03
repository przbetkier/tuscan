package io.github.przbetkier.tuscan.domain.latestProfiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "latestProfiles")
public class LatestProfile {

    @Id
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "avatarUrl")
    private String avatarUrl;
    @Column(name = "level")
    private Integer level;
    @Column(name = "elo")
    private Integer elo;
    @Column(name = "kdRatio")
    private BigDecimal kdRatio;
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    public LatestProfile() {
    }

    public LatestProfile(String nickname, String avatarUrl, Integer level, Integer elo, BigDecimal kdRatio,
                         LocalDateTime createdOn) {
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
