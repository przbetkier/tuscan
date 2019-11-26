package io.github.przbetkier.tuscan.adapter.api.request

import java.math.BigDecimal

data class DemoDetailsRequest(val matchId: String,
                              val data: List<PlayerData>)

data class PlayerData(val nickname: String,
                      val bombPlants: Number,
                      val defusals: Number,
                      val playersFlashed: Number,
                      val kills: List<Kill>,
                      val deaths: List<Death>
)

data class Kill(val victim: String,
                val killerPosition: Position,
                val victimPosition: Position,
                val wallbang: Boolean,
                val headshot: Boolean,
                val entry: Boolean,
                val weapon: String
)

data class Death(val killer: String,
                 val killerPosition: Position,
                 val victimPosition: Position,
                 val wallbang: Boolean,
                 val headshot: Boolean,
                 val entry: Boolean,
                 val weapon: String
)

data class Position(val X: BigDecimal,
                    val Y: BigDecimal)
