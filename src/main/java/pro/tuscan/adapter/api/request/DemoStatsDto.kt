package pro.tuscan.adapter.api.request

import java.math.BigDecimal

data class DemoStatsDto(val matchId: String,
                        val data: List<PlayerData>)

data class PlayerData(val nickname: String,
                      val plants: Number,
                      val defusals: Number,
                      val flashed: Number,
                      val kills: List<Kill>,
                      val deaths: List<Death>
)

data class Kill(val victim: String,
                val kPos: Position,
                val vPos: Position,
                val wb: Boolean,
                val hs: Boolean,
                val entry: Boolean,
                val weapon: String
)

data class Death(val killer: String,
                 val kPos: Position,
                 val vPos: Position,
                 val wb: Boolean,
                 val hs: Boolean,
                 val entry: Boolean,
                 val weapon: String
)

data class Position(val X: BigDecimal,
                    val Y: BigDecimal)
