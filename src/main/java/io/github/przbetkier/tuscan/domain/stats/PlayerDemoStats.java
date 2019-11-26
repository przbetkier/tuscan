package io.github.przbetkier.tuscan.domain.stats;

import java.util.List;

class PlayerDemoStats {

    private final String nickname;
    private final Integer bombPlants;
    private final Integer defusals;
    private final Integer playersFlashed;
    private final List<DemoKill> kills;
    private final List<DemoDeath> deaths;

    public PlayerDemoStats(String nickname, Integer bombPlants, Integer defusals, Integer playersFlashed,
                           List<DemoKill> kills, List<DemoDeath> deaths) {
        this.nickname = nickname;
        this.bombPlants = bombPlants;
        this.defusals = defusals;
        this.playersFlashed = playersFlashed;
        this.kills = kills;
        this.deaths = deaths;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getBombPlants() {
        return bombPlants;
    }

    public Integer getDefusals() {
        return defusals;
    }

    public Integer getPlayersFlashed() {
        return playersFlashed;
    }

    public List<DemoKill> getKills() {
        return kills;
    }

    public List<DemoDeath> getDeaths() {
        return deaths;
    }
}
