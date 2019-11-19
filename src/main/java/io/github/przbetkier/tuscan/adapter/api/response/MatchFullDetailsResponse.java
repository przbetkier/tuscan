package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult;
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team;

import java.util.List;

public class MatchFullDetailsResponse {

    private final String matchId;
    private final String map;
    private final String score;
    private final Integer roundsCount;
    private final List<Team> teams;
    private final String winnerTeam;
    private final MatchResult result;
    private final String demoUrl;

    public MatchFullDetailsResponse(String matchId, String map, String score, Integer roundsCount, List<Team> teams,
                                    String winnerTeam, MatchResult result, String demoUrl) {
        this.matchId = matchId;
        this.map = map;
        this.score = score;
        this.roundsCount = roundsCount;
        this.teams = teams;
        this.winnerTeam = winnerTeam;
        this.result = result;
        this.demoUrl = demoUrl;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMap() {
        return map;
    }

    public String getScore() {
        return score;
    }

    public Integer getRoundsCount() {
        return roundsCount;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getWinnerTeam() {
        return winnerTeam;
    }

    public MatchResult getResult() {
        return result;
    }

    public String getDemoUrl() {
        return demoUrl;
    }
}
