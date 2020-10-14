package pro.tuscan.adapter.api.response

data class PlayerSearchResponse(val players: List<PlayerSimple>)

class PlayerSimple(val nickname: String,
                   val lvl: Int,
                   val country: String,
                   val avatarUrl: String?,
                   val isVerified: Boolean)
