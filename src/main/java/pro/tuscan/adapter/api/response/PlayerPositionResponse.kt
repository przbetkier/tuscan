package pro.tuscan.adapter.api.response

data class PlayerPositionResponse(val playerId: String,
                                  val positionInRegion: Int,
                                  val positionInCountry: Int)
