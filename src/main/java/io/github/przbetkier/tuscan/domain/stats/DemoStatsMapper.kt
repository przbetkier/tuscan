package io.github.przbetkier.tuscan.domain.stats

import io.github.przbetkier.tuscan.adapter.api.request.Death
import io.github.przbetkier.tuscan.adapter.api.request.DemoDetailsRequest
import io.github.przbetkier.tuscan.adapter.api.request.Kill
import io.github.przbetkier.tuscan.adapter.api.request.Position
import java.time.Instant

class DemoStatsMapper {

    companion object {
        fun map(request: DemoDetailsRequest): DemoStats =
                request.let {
                    DemoStats(
                            it.matchId,
                            Instant.now(),
                            it.data.map { data ->
                                PlayerDemoStats(
                                        data.nickname,
                                        data.bombPlants.toInt(),
                                        data.defusals.toInt(),
                                        data.playersFlashed.toInt(),
                                        mapKills(data.kills),
                                        mapDeaths(data.deaths)
                                )
                            }
                    )
                }
        
        private fun mapKills(kills: List<Kill>): List<DemoKill> =
                kills.map {
                    DemoKill(
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
                            mapPosition(it.killerPosition),
                            mapPosition(it.victimPosition),
                            it.wallbang,
                            it.headshot,
                            it.entry,
                            it.weapon
                    )
                }

        private fun mapPosition(position: Position) =
                position.let {
                    DemoPosition(
                            it.X,
                            it.Y
                    )
                }
    }
}
