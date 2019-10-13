package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult;
import io.github.przbetkier.tuscan.adapter.api.response.dto.Player;
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats;
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team;
import io.github.przbetkier.tuscan.adapter.api.response.dto.TeamStats;
import io.github.przbetkier.tuscan.client.match.MatchFullDetailsDto;
import io.github.przbetkier.tuscan.client.match.MatchStatsDto;
import io.github.przbetkier.tuscan.client.match.PlayerDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult.LOSS;
import static io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult.WIN;
import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;

public class MatchFullDetailsMapper {

    private MatchFullDetailsMapper() {
    }

    public static MatchFullDetailsResponse map(MatchStatsDto matchStatsDto, String playerId) {
        MatchFullDetailsDto matchFullDetails = matchStatsDto.getMatchFullDetails().get(0);

        Team teamOne = getTeam(matchStatsDto, 0, getPlayers(matchStatsDto, 0));
        Team teamTwo = getTeam(matchStatsDto, 1, getPlayers(matchStatsDto, 1));

        return new MatchFullDetailsResponse(
                matchFullDetails.getMatchId(),
                matchFullDetails.getRoundStatsDto().getMap(),
                matchFullDetails.getRoundStatsDto().getScore(),
                valueOf(matchFullDetails.getRoundStatsDto().getRoundsCount()),
                asList(teamOne, teamTwo),
                matchFullDetails.getRoundStatsDto().getWinnerTeamId(),
                getResult(playerId, asList(teamOne, teamTwo), matchFullDetails.getRoundStatsDto().getWinnerTeamId()));
    }

    private static Player getPlayerFromPlayerDto(PlayerDto playerDto) {
        return new Player(
                playerDto.getPlayerId(), playerDto.getNickname(), new PlayerStats(
                valueOf(playerDto.getPlayerStats().getKills()),
                valueOf(playerDto.getPlayerStats().getAssists()),
                valueOf(playerDto.getPlayerStats().getDeaths()),
                valueOf(playerDto.getPlayerStats().getHeadshots()),
                valueOf(playerDto.getPlayerStats().getHeadshotPercentage()),
                mapKdRatio(playerDto),
                new BigDecimal(playerDto.getPlayerStats().getKrRatio()),
                valueOf(playerDto.getPlayerStats().getMvps()),
                valueOf(playerDto.getPlayerStats().getTripleKills()),
                valueOf(playerDto.getPlayerStats().getQuadroKills()),
                valueOf(playerDto.getPlayerStats().getPentaKills())));
    }

    private static BigDecimal mapKdRatio(PlayerDto playerDto) {
        String kdRatioString = playerDto.getPlayerStats().getKdRatio();
        BigDecimal kdRatio = new BigDecimal(kdRatioString);
        return kdRatio.compareTo(BigDecimal.ZERO) == 0
                ? new BigDecimal(playerDto.getPlayerStats().getKills())
                : kdRatio;
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
        return matchStatsDto.getMatchFullDetails().get(0).getTeams().get(teamNumber).getPlayers().stream().map(
                MatchFullDetailsMapper::getPlayerFromPlayerDto).collect(
                Collectors.toSet());
    }

    private static MatchResult getResult(String playerId, List<Team> teams, String winnerTeam) {
        Team winner = teams.stream().filter(t -> t.getTeamId().equals(winnerTeam)).findFirst().orElseThrow(RuntimeException::new);

        return winner.getPlayers().stream()
                .anyMatch(p -> p.getPlayerId().equals(playerId)) ? WIN : LOSS;
    }
}
