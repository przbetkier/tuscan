package pro.tuscan.domain.stats

import pro.tuscan.adapter.api.request.Death
import pro.tuscan.adapter.api.request.DemoStatsDto
import pro.tuscan.adapter.api.request.Kill
import pro.tuscan.adapter.api.request.PlayerData
import pro.tuscan.adapter.api.request.Position
import java.time.Instant

class DemoStatsMapper {

    companion object {
        fun mapFromDto(request: DemoStatsDto): DemoStats =
                request.let {
                    DemoStats(
                            it.matchId,
                            Instant.now(),
                            it.data.map { data ->
                                PlayerDemoStats(
                                        data.nickname,
                                        data.plants.toInt(),
                                        data.defusals.toInt(),
                                        data.flashed.toInt(),
                                        mapKills(data.kills),
                                        mapDeaths(data.deaths)
                                )
                            }
                    )
                }

        fun mapToDto(demoStats: DemoStats): DemoStatsDto =
                demoStats.let {
                    DemoStatsDto(
                            it.matchId,
                            it.stats.map { stats ->
                                PlayerData(
                                        stats.nickname,
                                        stats.bombPlants.toInt(),
                                        stats.defusals.toInt(),
                                        stats.enemiesFlashed.toInt(),
                                        mapToKills(stats.kills),
                                        mapToDeaths(stats.deaths)
                                )
                            }
                    )
                }

        private fun mapKills(kills: List<Kill>): List<DemoKill> =
                kills.map {
                    DemoKill(
                            it.victim,
                            mapPosition(it.kPos),
                            mapPosition(it.vPos),
                            it.wb,
                            it.hs,
                            it.entry,
                            it.weapon
                    )
                }

        private fun mapToKills(kills: List<DemoKill>): List<Kill> =
                kills.map {
                    Kill(
                            it.victim,
                            mapPosition(it.killerPosition),
                            mapPosition(it.victimPosition),
                            it.wallbang,
                            it.headshot,
                            it.entry,
                            it.weapon
                    )
                }

        private fun mapDeaths(kills: List<Death>): List<DemoDeath> =
                kills.map {
                    DemoDeath(
                            it.killer,
                            mapPosition(it.kPos),
                            mapPosition(it.vPos),
                            it.wb,
                            it.hs,
                            it.entry,
                            it.weapon
                    )
                }

        private fun mapToDeaths(kills: List<DemoDeath>): List<Death> =
                kills.map {
                    Death(
                            it.killer,
                            mapPosition(it.killerPosition),
                            mapPosition(it.victimPosition),
                            it.wallbang,
                            it.headshot,
                            it.entry,
                            it.weapon
                    )
                }

        private fun mapPosition(position: Position): DemoPosition =
                position.let {
                    DemoPosition(
                            it.X,
                            it.Y
                    )
                }

        private fun mapPosition(position: DemoPosition): Position =
                position.let {
                    Position(
                            it.x,
                            it.y
                    )
                }
    }
}
