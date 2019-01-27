package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class PlayerSimple {

    private final String nickname;
    private final int lvl;
    private final String country;
    private final String avatarUrl;
    private final boolean isVerified;

    public PlayerSimple(String nickname, int lvl, String country, String avatarUrl, boolean isVerified) {
        this.nickname = nickname;
        this.lvl = lvl;
        this.country = country;
        this.avatarUrl = avatarUrl;
        this.isVerified = isVerified;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLvl() {
        return lvl;
    }

    public String getCountry() {
        return country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
