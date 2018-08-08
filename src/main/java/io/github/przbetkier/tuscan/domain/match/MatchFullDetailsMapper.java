package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.Player;
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team;
import io.github.przbetkier.tuscan.adapter.api.response.dto.TeamStats;
import io.github.przbetkier.tuscan.domain.match.dto.stats.MatchFullDetailsDto;
import io.github.przbetkier.tuscan.domain.match.dto.stats.MatchStatsDto;
import io.github.przbetkier.tuscan.domain.match.dto.stats.PlayerDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;

class MatchFullDetailsMapper {

    private MatchFullDetailsMapper() {
    }

    static MatchFullDetailsResponse map(MatchStatsDto matchStatsDto) {
        MatchFullDetailsDto matchFullDetails = matchStatsDto.getMatchFullDetails().get(0);

        Team teamOne = getTeam(matchStatsDto, 0, getPlayers(matchStatsDto, 0));
        Team teamTwo = getTeam(matchStatsDto, 1, getPlayers(matchStatsDto, 1));

        return new MatchFullDetailsResponse(
                matchFullDetails.getMatchId(),
                matchFullDetails.getRoundStatsDto().getMap(),
                matchFullDetails.getRoundStatsDto().getScore(),
                valueOf(matchFullDetails.getRoundStatsDto().getRoundsCount()),
                asList(teamOne, teamTwo));
    }

    private static Player getPlayerFromPlayerDto(PlayerDto playerDto) {
        return new Player(
                playerDto.getPlayerId(), playerDto.getNickname(), new PlayerStats(
                valueOf(playerDto.getPlayerStats().getAssists()),
                valueOf(playerDto.getPlayerStats().getDeaths()),
                valueOf(playerDto.getPlayerStats().getHeadshots()),
                valueOf(playerDto.getPlayerStats().getHeadshotPercentage()),
                new BigDecimal(playerDto.getPlayerStats().getKdRatio()),
                new BigDecimal(playerDto.getPlayerStats().getKrRatio())
        ));
    }

    private static Team getTeam(MatchStatsDto matchStatsDto, Integer teamNumber, Set<Player> players) {
        return new Team(
                matchStatsDto.getMatchFullDetails().get(0).getTeams().get(teamNumber).getTeamId(),
                new TeamStats(
                        matchStatsDto.getMatchFullDetails().get(0).getTeams().get(teamNumber).getTeamStats().getTeamName(),
                        Double.valueOf(matchStatsDto.getMatchFullDetails().get(0).getTeams().get(teamNumber).getTeamStats().getHeadshotAvg())
                ),
                players);
    }

    private static Set<Player> getPlayers(MatchStatsDto matchStatsDto, Integer teamNumber) {
        Set<Player> players = new HashSet<>();
        matchStatsDto.getMatchFullDetails().get(0).getTeams().get(teamNumber).getPlayers().forEach(
                playerDto -> players.add(getPlayerFromPlayerDto(playerDto)));
        return players;
    }
}
