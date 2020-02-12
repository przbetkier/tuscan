package pro.tuscan.domain.player;

import pro.tuscan.adapter.api.response.dto.MapStats;
import pro.tuscan.adapter.api.response.dto.Performance;
import pro.tuscan.adapter.api.response.dto.SoloPerformance;
import pro.tuscan.adapter.api.response.dto.TeamPerformance;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerPerformanceMapper {

    private PlayerPerformanceMapper() {
    }

    public static Performance map(List<MapStats> mapStats) {
        return new Performance(
                new SoloPerformance(orderFromHighestKdRatio(mapStats).get(0).getCsgoMap(),
                                    orderFromHighestKdRatio(mapStats).get(0).getKdRatio()),
                new TeamPerformance(
                        orderFromHighestWinRate(mapStats).get(0).getCsgoMap(),
                        orderFromHighestWinRate(mapStats).get(0).getWinPercentage()
                ),
                new SoloPerformance(orderFromLowestKdRatio(mapStats).get(0).getCsgoMap(),
                        orderFromLowestKdRatio(mapStats).get(0).getKdRatio()),
                new TeamPerformance(
                        orderFromLowestWinRate(mapStats).get(0).getCsgoMap(),
                        orderFromLowestWinRate(mapStats).get(0).getWinPercentage()
                ));
    }

    static List<MapStats> orderFromHighestKdRatio(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getKdRatio).reversed())
                .collect(Collectors.toList());
    }

    private static List<MapStats> orderFromHighestWinRate(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getWinPercentage).reversed())
                .collect(Collectors.toList());
    }

    private static List<MapStats> orderFromLowestKdRatio(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getKdRatio))
                .collect(Collectors.toList());
    }

    private static List<MapStats> orderFromLowestWinRate(List<MapStats> mapStats) {
        return mapStats.stream()
                .sorted(Comparator.comparing(MapStats::getWinPercentage))
                .collect(Collectors.toList());
    }
}
